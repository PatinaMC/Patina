import java.util.Locale

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "patina"


for (name in listOf("Patina-API", "Patina-Server", "Patina-MojangAPI", "paper-api-generator")) {
    val projName = name.toLowerCase(Locale.ENGLISH)
    include(projName)
    findProject(":$projName")!!.projectDir = file(name)
}
