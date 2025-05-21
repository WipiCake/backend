FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY gradle gradle
COPY gradlew build.gradle settings.gradle* ./
RUN chmod +x gradlew && ./gradlew --no-daemon dependencies

COPY . .
RUN ./gradlew --no-daemon clean bootJar -x test

FROM eclipse-temurin:21-jre
WORKDIR /app

# build/libs/*.jar copy -> app.jar
COPY --from=build /workspace/build/libs/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]


