#build
FROM maven:3.9.8-eclipse-temurin-21 AS BUILD
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean install

#run
FROM eclipse-temurin:21-jre-jammy
RUN groupadd -f appuser && if [ $(id -u appuser > /dev/null 2>&1;echo $?) -ne 0 ]; then adduser --system --group appuser; fi
USER appuser
WORKDIR /home/appuser
COPY --chown=appuser:appuser entrypoint.sh /home/appuser/entrypoint.sh
COPY --from=BUILD --chown=appuser:appuser build/target/anagram-utils-*.jar /home/appuser/app.jar
RUN chmod 0540 /home/appuser/entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/home/appuser/entrypoint.sh"]
