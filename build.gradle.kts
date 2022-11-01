plugins {
    java
}

group = "by.it_academy"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.compileJava {
    options.compilerArgs.add("-parameters")
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")
    implementation("org.telegram:telegrambots:6.1.0")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    testCompileOnly("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.test {
    useJUnitPlatform()
}