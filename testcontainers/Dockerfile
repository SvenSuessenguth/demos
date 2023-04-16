FROM open-liberty:23.0.0.3-full-java17-openj9

COPY --chown=1001:0  /target/testcontainers.war /config/apps/
COPY --chown=1001:0 /src/main/liberty/config/server.xml /config/

