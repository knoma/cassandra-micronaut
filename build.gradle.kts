plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.0.0-M4"
}

version = "0.1"
group = "com.knoma"

repositories {
    mavenCentral()
}

val casandraDriverVersion: String by project


dependencies {
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("jakarta.annotation:jakarta.annotation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    compileOnly("org.graalvm.nativeimage:svm")

    implementation("io.micronaut.cassandra:micronaut-cassandra")
    implementation("com.datastax.oss:java-driver-core:$casandraDriverVersion")
    implementation("com.datastax.oss:java-driver-mapper-processor:$casandraDriverVersion")
    annotationProcessor("com.datastax.oss:java-driver-mapper-processor:$casandraDriverVersion")

    // RestAssured dependencies
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    runtimeOnly("org.yaml:snakeyaml")
}


application {
    mainClass.set("com.knoma.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.knoma.*")
    }
}



