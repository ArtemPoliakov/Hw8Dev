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
        classpath("com.h2database:h2:2.1.214")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("com.h2database:h2:2.1.214")
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:5.1.0")
}

flyway {
    url = "jdbc:h2:./data/office;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;"
    user = "user-name"
    password = "strong-password"
    schemas = arrayOf("public")
    locations = arrayOf("db.migration")
    cleanDisabled = false
    baselineOnMigrate = true
}

tasks.test {
    useJUnitPlatform()
}