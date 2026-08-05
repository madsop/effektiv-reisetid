FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:25.0-java25 AS native-build
COPY --chown=quarkus:quarkus gradlew /code/gradlew
COPY --chown=quarkus:quarkus gradle /code/gradle
COPY --chown=quarkus:quarkus build.gradle.kts /code/
COPY --chown=quarkus:quarkus settings.gradle.kts /code/
COPY --chown=quarkus:quarkus gradle.properties /code/
USER root
RUN chown -R quarkus:quarkus /code
USER quarkus
WORKDIR /code
RUN ./gradlew dependencies --no-daemon
COPY src /code/src
RUN ./gradlew build -Dquarkus.package.jar.type=native --no-daemon

# Create the docker final image
FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=native-build /code/build/*-runner /work/application
RUN chmod 775 /work
USER nonroot
EXPOSE 8080
ENTRYPOINT [ "/work/application" ]
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]