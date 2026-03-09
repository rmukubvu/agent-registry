# ── Stage 1: Build ────────────────────────────────────────────────────────────
# Build context = services/dna-enforcer/
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

# Gradle wrapper + service-local config
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle/ gradle/
COPY src/ src/

# Eclipse Temurin sets JAVA_HOME=/opt/java/openjdk. Tell Gradle's toolchain
# resolver to scan that path instead of auto-downloading a new JDK.
ENV JAVA_HOME=/opt/java/openjdk
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx2g \
  -Dorg.gradle.java.installations.auto-download=false \
  -Dorg.gradle.java.installations.fromEnv=JAVA_HOME"

RUN chmod +x gradlew && \
    ./gradlew quarkusBuild \
      -Dquarkus.package.type=fast-jar \
      --no-daemon \
      -x test

# ── Stage 2: Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:23-jre
WORKDIR /work

COPY --from=build /app/build/quarkus-app/lib/     lib/
COPY --from=build /app/build/quarkus-app/*.jar    ./
COPY --from=build /app/build/quarkus-app/app/     app/
COPY --from=build /app/build/quarkus-app/quarkus/ quarkus/

EXPOSE 8082

CMD ["java", "-jar", "quarkus-run.jar"]
