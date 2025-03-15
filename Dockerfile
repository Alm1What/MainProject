FROM eclipse-temurin:21-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

ENTRYPOINT ["java", "-jar", "target/MainPriject-0.0.1-SNAPSHOT.jar"]
