pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "Patina"

include("Patina-API", "Patina-Server", "Patina-MojangAPI")
