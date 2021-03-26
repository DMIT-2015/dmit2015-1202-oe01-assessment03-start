FROM openliberty/open-liberty:kernel-java11-openj9-ubi

COPY --chown=1001:0  target/dmit2015-oe01-assessment03-server.war /config/dropins/
COPY --chown=1001:0  server.xml /config

RUN configure.sh