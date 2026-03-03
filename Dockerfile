# ======================================================
# 1️⃣ BUILD STAGE
# ======================================================
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew

COPY src ./src

RUN ./gradlew clean bootJar --no-daemon -x test

# ======================================================
# 2️⃣ RUNTIME STAGE
# ======================================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SERVER_PORT=8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar app.jar"]