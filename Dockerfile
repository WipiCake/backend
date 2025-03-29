FROM eclipse-temurin:21-jdk

WORKDIR /app

# Gradle 캐시 최적화를 위해 먼저 gradle 관련 파일 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

# 의존성만 먼저 받아놓기 (캐시 활용)
RUN ./gradlew build -x test || return 0

# 전체 프로젝트 복사
COPY . .

# 빌드 (테스트는 생략 가능)
RUN ./gradlew build -x test

# 실행
ENTRYPOINT ["java", "-jar", "build/libs/wipi-0.0.1-SNAPSHOT.jar"]
