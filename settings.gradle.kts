import java.util.Locale

val forkName = "Patina"
val forkNameLowercase = forkName.toLowerCase(Locale.ENGLISH)

rootProject.name = forkNameLowercase

setupSubproject("$forkNameLowercase-api") {
    projectDir = File("API")
    buildFileName = "../subprojects/api.gradle.kts"
}
setupSubproject("$forkNameLowercase-server") {
    projectDir = File("Server")
    buildFileName = "../subprojects/server.gradle.kts"
}
setupSubproject("Yatoclip") { }

inline fun setupSubproject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}
