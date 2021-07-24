plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    id("io.papermc.paperweight.patcher") version "1.1.9-SNAPSHOT"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            onlyForConfigurations("paperclip")
        }
    }
    maven("https://maven.quiltmc.org/repository/release/") {
        content {
            onlyForConfigurations("remapper")
        }
    }
}

dependencies {
    remapper("org.quiltmc:tiny-remapper:0.4.1")
    paperclip("io.papermc:paperclip:2.0.1@jar")
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.isIncremental = true
        options.encoding = Charsets.UTF_8.name()
        options.release.set(16)
    }

    tasks.withType<Javadoc>().configureEach {
        options.encoding = Charsets.UTF_8.name()
    }

    tasks.withType<ProcessResources>().configureEach {
        filteringCharset = Charsets.UTF_8.name()
    }

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://ci.emc.gs/nexus/content/groups/aikar/")
        maven("https://repo.aikar.co/content/groups/aikar")
        maven("https://repo.md-5.net/content/repositories/releases/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://jitpack.io") // Airplane
    }
}

paperweight {
    serverProject.set(project(":Patina-Server"))

    usePaperUpstream(providers.gradleProperty("paperRef")) {
        withPaperPatcher {
            apiPatchDir.set(layout.projectDirectory.dir("patches/api"))
            apiOutputDir.set(layout.projectDirectory.dir("Patina-API"))

            serverPatchDir.set(layout.projectDirectory.dir("patches/server"))
            serverOutputDir.set(layout.projectDirectory.dir("Patina-Server"))
        }

        patchTasks {
            register("mojangApi") {
                isBareDirectory.set(true)
                upstreamDirPath.set("Paper-MojangAPI")
                patchDir.set(layout.projectDirectory.dir("patches/mojangapi"))
                outputDir.set(layout.projectDirectory.dir("Patina-MojangAPI"))
            }
        }
    }
}
tasks.paperclipJar {
    destinationDirectory.set(rootProject.layout.projectDirectory)
    archiveFileName.set("patina-paperclip.jar")
}
