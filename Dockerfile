FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/MainPriject-0.0.1-SNAPSHOT.jar app.jar
# Сюди можна відразу додати команду для переканфігуряції MainPriject-0.0.1-SNAPSHOT.jar
# і просто командою докер up все буде працювати самостійно
ENTRYPOINT ["java", "-jar", "app.jar"]