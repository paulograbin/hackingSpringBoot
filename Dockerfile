FROM eclipse-temurin:11.0.16.1_1-jre-jammy as builder
WORKDIR application
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:11.0.16.1_1-jre-jammy
WORKDIR application

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
#COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

#ENTRYPOINT ["java",  "-jar", "/app.jar"]
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]