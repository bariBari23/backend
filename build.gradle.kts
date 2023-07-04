import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.21"
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version "1.5.10"
    kotlin("kapt") version "1.3.61" // QueryDsl
    idea
}

group = "io.baribari"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val snippetsDir by extra { "build/generated-snippets" }
val querydslVersion = "5.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("mysql:mysql-connector-java")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.13")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    runtimeOnly("com.h2database:h2")

    // QueryDsl
    implementation("com.querydsl:querydsl-jpa:$querydslVersion")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.register("copyDocument", Copy::class) {
    dependsOn(tasks.asciidoctor)
    from("build/asciidoc/html5")
    into("src/main/resources/static/docs")
}

tasks.register("copyYml", Copy::class) {
    from("./backend-config")
    include("*.yml")
    into("src/main/resources")
}

tasks.register("copyYmlTest", Copy::class) {
    from("./backend-config/")
    include("*.yml")
    into("src/test/resources")
}


tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xemit-jvm-type-annotations")
            jvmTarget = "17"
        }
    }

    asciidoctor {
        doFirst {
            delete("src/main/resources/static/docs")
        }
        inputs.dir(snippetsDir)
        dependsOn(test)
    }

    build {
        dependsOn(getByName("copyYml"))
        dependsOn(getByName("copyYmlTest"))
        dependsOn(getByName("copyDocument"))
    }

}


allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}