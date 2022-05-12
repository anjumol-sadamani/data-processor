# Build Stage
FROM maven:3.6.3-openjdk-11-slim AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package -DskipTests=true

# Image creation stage
FROM openjdk:11.0-jre-buster
COPY --from=MAVEN_BUILD /target/data-processor.jar /data-processor.jar
EXPOSE 8080
CMD ["java", "-jar", "/data-processor.jar"]
