#!/bin/bash
scriptDir=$(dirname $0)

IMAGE_NAME=quay.io/jbcodeforce/saga-lra-coordinator

if [[ $# -eq 1 ]]
then
  TAG=$1
else
  TAG=latest
fi

./mvnw clean package  -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t  ${IMAGE_NAME}:${TAG} .
docker tag  ${IMAGE_NAME}:${TAG}   ${IMAGE_NAME}:latest
docker push ${IMAGE_NAME}:${TAG}
docker push ${IMAGE_NAME}:latest