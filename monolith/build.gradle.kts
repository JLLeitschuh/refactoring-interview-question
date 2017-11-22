
dependencies {
    // Monolith must depend upon the `library` project.
    api(project(":library"))
    // Monolith must depend upon the `external` project.
    implementation(project(":external"))
    implementation(group = "javax.inject", name = "javax.inject", version = "1")
}
