#!/bin/sh
if [ $(docker ps -a -f name=dmit2015-oe01-assessment03-client | grep -w dmit2015-oe01-assessment03-client | wc -l) -eq 1 ]; then
  docker rm -f dmit2015-oe01-assessment03-client
fi
mvn clean package && docker build -t dmit2015/dmit2015-oe01-assessment03-client .
docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-oe01-assessment03-client dmit2015/dmit2015-oe01-assessment03-client
