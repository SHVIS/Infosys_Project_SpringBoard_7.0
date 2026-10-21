
# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY mvnw /app/mvnw
COPY .mvn/ /app/.mvn/
COPY pom.xml /app/pom.xml
COPY src/ /app/src/

RUN chmod +x /app/mvnw && /app/mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
