#!/bin/bash
source /etc/profile

ARTIFACT=$1
VERSION=$2
GIT=$3
BRANCH=$4
PRO_DIR=$5

### set BRANCH default master
if [ -z ${BRANCH} ] ; then
    BRANCH=master
fi
### set TAG_NAME TAG_INFO
if [ ${BRANCH} == "master" ] ; then
    TAG_NAME=V_${VERSION}
    TAG_INFO=", \"tag\": \"$TAG_NAME\""
fi

if [ -z ${ARTIFACT} ] || [ -z ${VERSION} ] || [ -z ${GIT} ] ; then
    echo "require 3 params: Artifact, Version, Git; options: Branch, PRO_DIR; default branch is master"
    exit -1
else
    echo "build params ARTIFACT: $ARTIFACT, VERSION: $VERSION, GIT: $GIT, BRANCH: $BRANCH, TAG_NAME: $TAG_NAME, PRO_DIR: $PRO_DIR"
fi

### there static can change by project
DEFAULT_V=0.0.1-SNAPSHOT
REGISTRY_DIR=/data/registry
WORKSPACE=/data/msc-ws

TIME=`date "+%Y-%m-%d %H:%M"`

BRANCH_WS=${WORKSPACE}/${BRANCH}
PROJECT_DIR=${BRANCH_WS}/${PRO_DIR}
ARTIFACT_DIR=${BRANCH_WS}/${PRO_DIR}/${ARTIFACT}
RELEASE_INFO=src/main/resources/release.info
RELEASE_DIR=${REGISTRY_DIR}/${BRANCH}/${ARTIFACT}/${VERSION}
R_DIR_INFO=${RELEASE_DIR}/release.info

function mkReleaseDir(){
    if [ ! -e "$RELEASE_DIR" ] ; then
        mkdir -p ${RELEASE_DIR}
        checkCmd "prepare registry release folder error"
    fi
}

function errorExit(){
    echo "$1"
    mkReleaseDir
    echo "{\"name\": \"$ARTIFACT\", \"version\": \"$VERSION\", \"time\": \"$TIME\"$TAG_INFO, \"error\": \"$1\"}" > ${R_DIR_INFO}
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
    ### valid project & artifact
    if [ ! -e "$PROJECT_DIR" ] ; then
        mkdir -p ${BRANCH_WS}
        checkCmd "create $ARTIFACT branch $BRANCH workspace error"
        cd ${BRANCH_WS}
        git clone ${GIT} -b ${BRANCH}
        checkCmd "git clone project $ARTIFACT error"
    fi
    ### git pull project source
    cd ${PROJECT_DIR}
    git pull
    checkCmd "git pull $PRO_DIR from $GIT error"
    echo "git pull $PRO_DIR done"
    mkReleaseDir
    echo "prepare $ARTIFACT registry release version folder done"
}

### update release info
function updateReleaseInfo(){
    cd ${ARTIFACT_DIR}
    echo "{\"name\": \"$ARTIFACT\", \"version\": \"$VERSION\", \"time\": \"$TIME\"$TAG_INFO}" > ${RELEASE_INFO}
    echo "new release tag: $VERSION created for $ARTIFACT"
}

### build artifact and do git tag
function buildArtifact(){
    cd ${PROJECT_DIR}
    mvn -Dmaven.test.skip=true clean install -U
    checkCmd "mvn clean package to build $PRO_DIR error"
    local ARTIFACT_FILE=${RELEASE_DIR}/${ARTIFACT}.jar
    cp ${ARTIFACT_DIR}/target/${ARTIFACT}-*.jar ${ARTIFACT_FILE}
    checkCmd "save archived file to registry $ARTIFACT_FILE error"
    cp ${ARTIFACT_DIR}/${RELEASE_INFO} ${R_DIR_INFO}
    checkCmd "save release.info file to registry $R_DIR_INFO error"
    echo "build done and save archived file into registry $RELEASE_DIR"
}

prepareSource
updateReleaseInfo
buildArtifact
