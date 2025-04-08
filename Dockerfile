FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build.gradle ./
COPY gradle ./gradle
COPY gradlew ./

RUN ./gradlew build -x test || true

COPY . .

RUN ./gradlew build -x test

ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
