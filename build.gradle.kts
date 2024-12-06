plugins {
    application
    kotlin("jvm") version "1.9.25"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}

application {
    mainClass = "Day06Kt"
}