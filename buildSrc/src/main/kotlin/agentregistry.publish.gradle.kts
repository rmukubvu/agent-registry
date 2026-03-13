plugins {
    `maven-publish`
    signing
}

configure<PublishingExtension> {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("Agent DNA API")
                description.set("This combined specification exposes the Agent DNA Registry and Agent DNA\nEnforcer in a single OpenAPI document. It is intended for generating one SDK\nwith separate logical surfaces for registry management and runtime enforcement.")
                url.set("https://www.github.com/rmukubvu/agent-registry")

                licenses {
                    license {
                        name.set("Apache-2.0")
                    }
                }

                developers {
                    developer {
                        name.set("Agentregistry")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/rmukubvu/agent-registry.git")
                    developerConnection.set("scm:git:git://github.com/rmukubvu/agent-registry.git")
                    url.set("https://github.com/rmukubvu/agent-registry")
                }

                versionMapping {
                    allVariants {
                        fromResolutionResult()
                    }
                }
            }
        }
    }
    repositories {
        if (project.hasProperty("publishLocal")) {
            maven {
                name = "LocalFileSystem"
                url = uri("${rootProject.layout.buildDirectory.get()}/local-maven-repo")
            }
        }
    }
}

signing {
    val signingKeyId = System.getenv("GPG_SIGNING_KEY_ID")?.ifBlank { null }
    val signingKey = System.getenv("GPG_SIGNING_KEY")?.ifBlank { null }
    val signingPassword = System.getenv("GPG_SIGNING_PASSWORD")?.ifBlank { null }
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(
            signingKeyId,
            signingKey,
            signingPassword,
        )
        sign(publishing.publications["maven"])
    }
}

tasks.named("publish") {
    dependsOn(":closeAndReleaseSonatypeStagingRepository")
}
