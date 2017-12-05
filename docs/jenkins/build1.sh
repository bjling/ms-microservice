#!/bin/sh

source /etc/profile
PROJECT=$1
VERSION=$2
GIT=$3
BRANCH=$4

### set BRANCH default master
if [ -z "${BRANCH}" ] ; then
    BRANCH=master
fi
### set TAG_NAME TAG_INFO
if [ ${BRANCH} == "master" ] ; then
    TAG_NAME=V_${VERSION}
    TAG_INFO=", \"tag\": \"$TAG_NAME\""
fi

if [ -z ${PROJECT} ] || [ -z ${VERSION} ] || [ -z ${GIT} ] ; then
    echo "require 3 params: PROJECT, Version, Description, Git; options: Branch; default branch is master"
    exit -1
else
    echo "build params PROJECT: $PROJECT, VERSION: $VERSION, GIT: $GIT, BRANCH: $BRANCH, TAG_NAME: $TAG_NAME"
fi

### there static can change by project
DEFAULT_V=0.0.1-SNAPSHOT
REGISTRY_DIR=/data/registry
WORKSPACE=/data/msc-ws

TIME=`date "+%Y-%m-%d %H:%M"`

BRANCH_WS=${WORKSPACE}/${BRANCH}
PRO_DIR=${BRANCH_WS}/${PROJECT}
RELEASE_INFO=src/main/resources/release.info
RELEASE_DIR=${REGISTRY_DIR}/${BRANCH}/${PROJECT}/${VERSION}
R_DIR_INFO=${RELEASE_DIR}/release.info

function mkReleaseDir(){
    if [ ! -e "${RELEASE_DIR}" ] ; then
        mkdir -p ${RELEASE_DIR}
        checkCmd "prepare registry release folder error"
    fi
}

function errorExit(){
    echo "$1"
    mkReleaseDir
    echo "{\"name\": \"$PROJECT\", \"version\": \"$VERSION\", \"time\": \"$TIME\"$TAG_INFO, \"error\": \"$1\"}" > ${R_DIR_INFO}
    exit -1
}

### check pre cmd if error then echo $1 and exit
function checkCmd(){
    if [ $? != 0 ]; then
        errorExit "$1"
    fi
}

### prepare project source
function prepareSource(){
    ### valid project 
    if [ ! -e "$PRO_DIR" ] ; then
        mkdir -p ${BRANCH_WS}
        checkCmd "create $PROJECT branch $BRANCH workspace error"
        cd ${BRANCH_WS}
        git clone ${GIT} -b ${BRANCH}
        checkCmd "git clone project $PROJECT error"
    fi
    ### git pull project source
    cd ${PRO_DIR}
    git pull
    checkCmd "git pull $PRO_DIR from $GIT error"
    echo "git pull $PRO_DIR done"
    mkReleaseDir
    echo "prepare $PROJECT registry release version folder done"
}

### update release info
function updateReleaseInfo(){
    cd ${PRO_DIR}
    echo "{\"name\": \"$PROJECT\", \"version\": \"$VERSION\", \"time\": \"$TIME\"$TAG_INFO}" > ${RELEASE_INFO}
}

### build project and do git tag
function buildProject(){
    cd ${PRO_DIR}
    mvn -Dmaven.test.skip=true clean install -U
    checkCmd "mvn clean install to build $PRO_DIR error"
    local PROJECT_FILE=${RELEASE_DIR}/${PROJECT}.jar
    cp ${PRO_DIR}/target/${PROJECT}-*.jar ${PROJECT_FILE}
    checkCmd "save archived file to registry ${PROJECT_FILE} error"
    cp ${PRO_DIR}/${RELEASE_INFO} ${R_DIR_INFO}
    checkCmd "save release.info file to registry $R_DIR_INFO error"
    echo "build done and save archived file into registry $RELEASE_DIR"
}

prepareSource
updateReleaseInfo
buildProject
