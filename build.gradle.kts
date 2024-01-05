plugins {
    id("java")
    id ("org.flywaydb.flyway") version "10.0.0"
}

group = "com.homework"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

buildscript {
    dependencies {
        classpath("com.h2database:h2:2.2.220")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    testImplementation("com.h2database:h2:2.2.220")
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:5.1.0")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-core
    implementation("org.flywaydb:flyway-core:10.4.1")
}

flyway {
    url = "jdbc:h2:mem:office;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;"
    user = "user-name"
    password = "strong-password"
    cleanDisabled = false
    baselineOnMigrate = true
}

tasks.test {
    useJUnitPlatform()
}