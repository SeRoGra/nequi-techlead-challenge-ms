FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY ./applications/app-service/build/libs/nequi-techlead-challenge-ms.jar nequi-techlead-challenge-ms.jar

ENV JAVA_OPTS="-XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

USER appuser

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar nequi-techlead-challenge-ms.jar" ]