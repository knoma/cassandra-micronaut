plugins {
    id("com.gradleup.shadow") version "8.3.9"
    id("io.micronaut.application") version "4.6.0"
    id("io.micronaut.aot") version "4.6.0"
}

version = "0.1"
group = "com.knoma"

val casandraDriverVersion=project.properties.get("casandraDriverVersion")

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micrometer:context-propagation")
    implementation("io.micronaut.cassandra:micronaut-cassandra")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("io.micronaut:micronaut-http-client")

    implementation("com.datastax.oss:java-driver-mapper-runtime:$casandraDriverVersion")
    annotationProcessor("com.datastax.oss:java-driver-mapper-processor:$casandraDriverVersion")
}


application {
    mainClass = "com.knoma.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("25")
    targetCompatibility = JavaVersion.toVersion("25")
}

graalvmNative.toolchainDetection = false
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.knoma.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "25"
}

