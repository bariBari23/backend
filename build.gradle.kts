import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.21"
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

group = "io.baribari"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val snippetsDir by extra { "build/generated-snippets" }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("mysql:mysql-connector-java")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.13")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    runtimeOnly("com.h2database:h2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.register("copyDocument", Copy::class) {
    dependsOn(tasks.asciidoctor)
    from("build/asciidoc/html5")
    into("src/main/resources/static/docs")
}

tasks.register("copyYml", Copy::class){
    from("./backend-config")
    include("*.yml")
    into("src/main/resources")
}

tasks.register("copyYmlTest", Copy::class){
    from("./backend-config/")
    include("*.yml")
    into("src/test/kotlin/store/baribari/demo")
}


tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    test {
        useJUnitPlatform()
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        doFirst {
            delete("src/main/resources/static/docs")
        }
        inputs.dir(snippetsDir)
        dependsOn(test)
    }
}


tasks.build{
    dependsOn(tasks.getByName("copyYml"))
    dependsOn(tasks.getByPath("copyYmlTest"))
    dependsOn(tasks.getByName("copyDocument"))
}
