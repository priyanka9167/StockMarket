plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok:1.18.22")
    implementation("com.google.firebase:firebase-admin:9.1.1")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.0.3")
    implementation("org.springframework.kafka:spring-kafka:3.1.1")
    implementation("org.projectlombok:lombok:1.18.28")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

springBoot {
    mainClass.set("org.example.StockExchangeApplication")
}

tasks.test {
    useJUnitPlatform()
}
