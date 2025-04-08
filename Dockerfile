FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build.gradle ./
COPY gradle ./gradle
COPY gradlew ./
RUN chmod +x gradlew
RUN ./gradlew dependencies || true

COPY . .

RUN ./gradlew build -x test

ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
