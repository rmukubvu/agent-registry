# ── Stage 1: Build ────────────────────────────────────────────────────────────
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

# Gradle wrapper + root config
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle/ gradle/

# Both services (settings.gradle references both)
COPY services/ services/

# Disable toolchain auto-download so Gradle uses the container JDK
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx2g -Dorg.gradle.java.installations.auto-download=false"

RUN chmod +x gradlew && \
    ./gradlew :services:dna-registry:quarkusBuild \
      -Dquarkus.package.type=fast-jar \
      --no-daemon \
      -x test

# ── Stage 2: Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:23-jre
WORKDIR /work

COPY --from=build /app/services/dna-registry/build/quarkus-app/lib/     lib/
COPY --from=build /app/services/dna-registry/build/quarkus-app/*.jar    ./
COPY --from=build /app/services/dna-registry/build/quarkus-app/app/     app/
COPY --from=build /app/services/dna-registry/build/quarkus-app/quarkus/ quarkus/

EXPOSE 8081

# Quarkus reads QUARKUS_* env vars; override port via env if needed
ENV QUARKUS_HTTP_PORT=8081

CMD ["java", "-jar", "quarkus-run.jar"]
