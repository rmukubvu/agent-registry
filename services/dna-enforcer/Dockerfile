# ── Stage 1: Build ────────────────────────────────────────────────────────────
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

# Gradle wrapper + root config
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle/ gradle/

# Both services (settings.gradle references both)
COPY services/ services/

# Eclipse Temurin sets JAVA_HOME=/opt/java/openjdk. Tell Gradle's toolchain
# resolver to scan that path instead of auto-downloading a new JDK.
ENV JAVA_HOME=/opt/java/openjdk
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx2g \
  -Dorg.gradle.java.installations.auto-download=false \
  -Dorg.gradle.java.installations.fromEnv=JAVA_HOME"

RUN chmod +x gradlew && \
    ./gradlew :services:dna-enforcer:quarkusBuild \
      -Dquarkus.package.type=fast-jar \
      --no-daemon \
      -x test

# ── Stage 2: Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:23-jre
WORKDIR /work

COPY --from=build /app/services/dna-enforcer/build/quarkus-app/lib/     lib/
COPY --from=build /app/services/dna-enforcer/build/quarkus-app/*.jar    ./
COPY --from=build /app/services/dna-enforcer/build/quarkus-app/app/     app/
COPY --from=build /app/services/dna-enforcer/build/quarkus-app/quarkus/ quarkus/

EXPOSE 8082
ENV QUARKUS_HTTP_PORT=8082

CMD ["java", "-jar", "quarkus-run.jar"]
