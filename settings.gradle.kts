pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "patina"

include("patina-api", "patina-server", "patina-mojangapi")
