FROM gradle:6.7-openj9 as builder
# Copy local code to the container image.
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
# Build a release artifact.
RUN gradle clean shadowJar --no-daemon --stacktrace

FROM openjdk:8-jre-alpine
ENV APPROOT="/app"
WORKDIR $APPROOT
COPY --from=builder /home/gradle/build/libs/metrics-collection-1.0-all.jar ${APPROOT}/metrics-collection-1.0-all.jar
ENTRYPOINT ["java"]
CMD ["-jar","-Xmx128m","-Xms128m","-Djava.security.egd=file:/dev/./urandom", "/app/metrics-collection-1.0-all.jar"]
EXPOSE 8080
