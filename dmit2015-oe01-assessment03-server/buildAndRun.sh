#!/bin/sh
if [ $(docker ps -a -f name=dmit2015-oe01-assessment03-server | grep -w dmit2015-oe01-assessment03-server | wc -l) -eq 1 ]; then
  docker rm -f dmit2015-oe01-assessment03-server
fi
mvn clean package && docker build -t dmit2015/dmit2015-oe01-assessment03-server .
docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-oe01-assessment03-server dmit2015/dmit2015-oe01-assessment03-server
