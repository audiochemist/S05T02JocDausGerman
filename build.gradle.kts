plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "cat.itacademy.barcelonactiva.medina.jocdaus.s05.t01.n02"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("javax.servlet", "javax.servlet-api", "4.0.1")
	implementation ("io.jsonwebtoken:jjwt-api:0.12.5")
	implementation("org.mockito:mockito-core:5.12.0")
	implementation("org.modelmapper:modelmapper:2.1.1")

	//implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("com.google.code.gson:gson:2.10.1")



	developmentOnly("org.springframework.boot:spring-boot-devtools")

	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
	testImplementation("org.mockito:mockito-core:4.0.0")
	testImplementation ("au.com.dius.pact.consumer:junit5:4.3.0")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	compileOnly("org.projectlombok:lombok")
	implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")

	runtimeOnly("com.mysql:mysql-connector-j")
	//runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.5")
	//runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.12.5")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
