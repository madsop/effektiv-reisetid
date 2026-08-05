FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:25.0-java25 AS native-build
ARG version=v24.19.0
COPY --chown=quarkus:quarkus gradlew /code/gradlew
COPY --chown=quarkus:quarkus gradle /code/gradle
COPY --chown=quarkus:quarkus build.gradle.kts /code/
COPY --chown=quarkus:quarkus settings.gradle.kts /code/
COPY --chown=quarkus:quarkus gradle.properties /code/
USER root
RUN chown -R quarkus:quarkus /code
RUN ARCH=$(uname -m) && \
    case "$ARCH" in \
        x86_64)  NODE_ARCH="x64" ;; \
        aarch64) NODE_ARCH="arm64" ;; \
        *) echo "Unsupported arch: $ARCH" && exit 1 ;; \
    esac && \
    microdnf install -y curl tar gzip python3 make gcc gcc-c++ \
    && curl -fsSL https://nodejs.org/dist/$version/node-$version-linux-${NODE_ARCH}.tar.gz -o node.tar.gz \
    && tar -xzf node.tar.gz -C /usr/local --strip-components=1
RUN mkdir -p /home/quarkus/.npm && chown -R quarkus:quarkus /home/quarkus
ENV NPM_CONFIG_CACHE=/home/quarkus/.npm
USER quarkus
WORKDIR /code
RUN ./gradlew dependencies --no-daemon
COPY --chown=quarkus:quarkus src /code/src
RUN ./gradlew build -Dquarkus.native.enabled=true -Dquarkus.package.jar.enabled=false -Dquarkus.quinoa.package-manager-install=false --no-daemon

# Create the docker final image
FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY --from=native-build /code/build/*-runner /work/application
RUN chmod 775 /work
USER nonroot
EXPOSE 8080
ENTRYPOINT [ "/work/application" ]
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]