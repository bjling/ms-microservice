#!/bin/bash
source /etc/profile
PROJECT=$1
VERSION=$2
DEPLOY_E=$3
DEPLOY_WS=$4
BRANCH=$5

if [ -z ${PROJECT} ] || [ -z ${VERSION} ] || [ -z ${DEPLOY_E} ] || [ -z ${DEPLOY_WS} ] ; then
    echo "require 4 params: Project, Version, Branch, DeployEnv, DeployWorkspace; options: Branch"
    exit -1
else
    echo "start service params PROJECT: $PROJECT, VERSION: $VERSION, DEPLOY_E: $DEPLOY_E, DEPLOY_WS: $DEPLOY_WS BRANCH: $BRANCH"
fi

LOG_NAME=process
TIME=`date "+%Y%m%d%H%M%S"`
DEPLOY_DIR=${DEPLOY_WS}/${PROJECT}
SERVER_ID=${DEPLOY_DIR}/server.pid
DOWNLOAD_URL=http://java-msc.yupaopao.cn:81/${BRANCH}/${PROJECT}/${VERSION}/${PROJECT}.jar

function errorExit(){
    echo $1
    exit -1
}

function checkCmd(){
    if [ $? != 0 ]; then
        errorExit "$1"
    fi
}

function checkStartEnv (){
    if [ "$DEPLOY_E" != "dev" ]  && [ "$DEPLOY_E" != "test" ]  && [ "$DEPLOY_E" != "uat" ]  && [ "$DEPLOY_E" != "prod" ]; then
        errorExit "start $PROJECT with bad env: $DEPLOY_E"
    fi
}

function getVersionProject(){
    if [ ! -z ${BRANCH} ]; then
        if [ ! -e ${DEPLOY_DIR} ]; then
            mkdir -p ${DEPLOY_DIR}
        fi
        cd ${DEPLOY_DIR}
        if [ -f ${PROJECT}.jar ] ; then
            rm ${PROJECT}.jar
        fi
        wget ${DOWNLOAD_URL}
        checkCmd "download project $PROJECT with version $VERSION error"

        if [ ! -f ${PROJECT}.jar ] ; then
            errorExit "can not find project $PROJECT with version $VERSION use branch $BRANCH from: $DOWNLOAD_URL"
        fi
        echo "download project $PROJECT with version $VERSION use branch $BRANCH done"
    fi
}

function backupProject(){
    if [ ! -z ${BRANCH} ]; then
        cd ${DEPLOY_DIR}
        if [ ! -e backup ]; then
            mkdir backup
        fi
        if [ -f ${PROJECT}-*.jar ] ; then
            mv ${PROJECT}-*.jar backup/
            checkCmd "backup project $PROJECT error"
        fi
        ### rename the wget jar
        mv ${PROJECT}.jar ${PROJECT}-${VERSION}.jar
        checkCmd "rename project $PROJECT.jar to $PROJECT-$VERSION.jar error"
        echo "backup $PROJECT done"
    fi
}

function runProject(){
    if [ -f ${SERVER_ID} ] ; then
        local PID="$(cat ${SERVER_ID})"
        if [ -n "$PID" ] ; then
            echo "killing old $PROJECT, pid: $PID"
            kill -9 ${PID}
            rm -rf ${SERVER_ID}
            checkCmd "stop the old $PROJECT agent error"
        fi
        echo "=====>wait about 3s to stop the old $PROJECT agent"
        sleep 3
    fi
    local P_LOG=${DEPLOY_DIR}/log
    if [ -e "$P_LOG" ] && [ -f "$P_LOG/$LOG_NAME.log" ]; then
        mv ${P_LOG}/${LOG_NAME}.log ${P_LOG}/${LOG_NAME}_${TIME}.log
    fi

    local MEMOPS="-server -Xms1536M -Xmx1536M -XX:+UseNUMA -XX:+UseParallelGC -XX:-UseGCOverheadLimit -XX:NewRatio=1 -XX:MaxDirectMemorySize=1600M"
    cd ${DEPLOY_DIR}
    nohup java ${MEMOPS} -jar ${PROJECT}-${VERSION}.jar --spring.profiles.active=${DEPLOY_E}>nohup.out 2>&1 &
    checkCmd "run java $PROJECT-$VERSION.jar error"
}

### check project agent start with 30s
function checkAgent(){
    echo "=====> wait about 5s now valid the new $PROJECT agent"
    sleep 5
    local STARTED=0
    local PID=0
    for (( i=0; i<6 ; i++ )) ; do
        PID=`ps -ef | grep ${PROJECT}-${VERSION}.jar | grep java | grep -v grep | awk '{print $2}'`
        if [ -n "$PID" ]; then
            STARTED=1
            break
        else
            if [ ${i} == 5 ] ; then
                cat ${DEPLOY_DIR}/nohup.out
                errorExit "the project $PROJECT with version $VERSION agent start error"
            fi
            echo "=====> wait about 5s now valid the new $PROJECT agent"
            sleep 5
        fi
    done

    echo "=====> wait about 5s to double confirm the new $PROJECT agent"
    sleep 5
    ### double check project agent started
    PID=`ps -ef | grep ${PROJECT}-${VERSION}.jar | grep java | grep -v grep | awk '{print $2}'`
    if [ ${STARTED} == 1 ] && [ -n "$PID" ]; then
        cd ${DEPLOY_DIR}
        echo ${PID} > ${SERVER_ID}
        echo "the project $PROJECT with version $VERSION agent start done, PID: $PID"
    else
        cat ${DEPLOY_DIR}/nohup.out
        errorExit "the project $PROJECT with version $VERSION agent start error"
    fi
}

checkStartEnv
getVersionProject
backupProject
runProject
checkAgent
