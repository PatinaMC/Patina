plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0" apply false
    id("io.papermc.paperweight.patcher") version "1.2.0"
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
    remapper("net.fabricmc:tiny-remapper:0.6.0:fat")
    decompiler("net.minecraftforge:forgeflower:1.5.498.12")
    paperclip("io.papermc:paperclip:2.0.1")
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(16)
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
            remapRepo.set("https://maven.quiltmc.org/repository/release/")
            decompileRepo.set("https://files.minecraftforge.net/maven/")

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
