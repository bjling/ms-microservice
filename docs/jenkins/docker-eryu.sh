#!/bin/bash

PROJECT=$1
ARTIFACT=$2
VERSION=$3
DEPLOY_E=$4
PORT=$5
MEM="-server -Xms1600M -Xmx1600M -XX:+UseNUMA -XX:+UseParallelGC -XX:-UseGCOverheadLimit -XX:NewRatio=1 -XX:MaxDirectMemorySize=1840M"

function checkCmd(){
    if [ $? != 0 ]; then
        echo "$1"
        exit -2
    fi
}

function errorExit(){
    echo $1
    exit -3
}

function usage(){
        echo "USAGE $0 PROJECT ARTIFACT VERSION DEPLOY_E  DPORT"
        exit -1
}

function checkStartEnv (){
    if [ "$DEPLOY_E" != "dev" ]  && [ "$DEPLOY_E" != "test" ]  && [ "$DEPLOY_E" != "uat" ]  && [ "$DEPLOY_E" != "prod" ]; then
        echo "start $ARTIFACT with bad env: $DEPLOY_E"
    fi
}

if [ $# -ne 5 ]
   then
     usage
fi

checkStartEnv
OldARTIF="`docker ps -a | grep ${ARTIFACT} | awk '{print $NF}'`"

function StopOldARTIF(){
if [ -z ${OldARTIF} ]
   then
       echo ${ARTIFACT} is not runing, Ready for starting ${ARTIFACT}
else
       docker stop ${OldARTIF}
       checkCmd "Stop ${OldARTIF} error"
       docker rm ${OldARTIF}
fi
}

REGISTRY=registry.eryufm.cn
ImageVerO=`docker ps -a | grep ${ARTIFACT} | awk '{print $2}'`
ImageVerN="$REGISTRY/${PROJECT}/${ARTIFACT}:${VERSION}"

if [ -z ${ImageVerO} ]
    then 
      docker pull ${ImageVerN}
else
    if [ "${ImageVerO}" != "${ImageVerN}" ]
       then 
          docker pull ${ImageVerN}
          StopOldARTIF
          docker rmi ${ImageVerO}
    else
        echo " ${ImageVerN} is already exist! "
    fi
fi

docker run -d -e "SPRING_PROFILES_ACTIVE=${DEPLOY_E}" -e "JAVA_OPTS=${MEM}" --memory 2048m -p $PORT:$PORT --name=${ARTIFACT} -v /data/logs:/logs $REGISTRY/${PROJECT}/${ARTIFACT}:${VERSION}

checkCmd "Start ${ARTIFACT} microservice error."

function checkdockerAgent(){
    echo "=====> wait about 5s to starting ${ARTIFACT} microservice."
    local STARTED=0
    local D_PPID=0
    for (( i=0; i<6 ; i++ )) ; do
        D_PPID=`docker top ${ARTIFACT}| awk 'NR==3{print $3}'` 
        if [ -n "${D_PPID}" ]; then
            STARTED=1
            break
        else
            if [ ${i} == 5 ] ; then
                docker logs ${ARTIFACT}
                errorExit "the project ${ARTIFACT} with version $VERSION microservice start error"
            fi
            echo "=====> wait about 5s now valid the new ${ARTIFACT} microservice"
            sleep 5
        fi
    done
    echo "=====> wait about 20s to double confirm the new ${ARTIFACT} microservice"
    sleep 20
    ### double check ${ARTIFACT} microservice started
    D_PPID=`docker top ${ARTIFACT}| awk 'NR==3{print $3}'` 
    if [ ${STARTED} == 1 ] && [ -n "${D_PPID}" ]; then
        echo "the project ${ARTIFACT} with version $VERSION microservice start done, PID: ${D_PPID}"
    else
        docker logs ${ARTIFACT}
        errorExit "the project ${ARTIFACT} with version $VERSION microservice start error"
    fi
}
checkdockerAgent
