FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

ARG APP_NAME=routeadvisor
ARG APP_VERSION=0.0.1-SNAPSHOT

RUN adduser --system "$APP_NAME" && mkdir /log  && chown "$APP_NAME" /log

USER $APP_NAME

COPY target/"$APP_NAME"-"$APP_VERSION".jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
