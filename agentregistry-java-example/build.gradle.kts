plugins {
    id("agentregistry.java")
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":agentregistry-java-core"))
    implementation(project(":agentregistry-java-client-okhttp"))
}

tasks.withType<JavaCompile>().configureEach {
    // Allow using more modern APIs, like `List.of` and `Map.of`, in examples.
    options.release.set(9)
}

application {
    // Use `./gradlew :agentregistry-java-example:run` to run `Main`
    // Use `./gradlew :agentregistry-java-example:run -Pexample=Something` to run `SomethingExample`
    mainClass = "com.agentregistry.api.example.${
        if (project.hasProperty("example"))
            "${project.property("example")}Example"
        else
            "Main"
    }"
}
