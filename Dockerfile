# ======================================================
# 1️⃣ Etapa de construcción (usando Gradle Wrapper)
# ======================================================
FROM eclipse-temurin:17-jdk AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de Gradle (para aprovechar la caché)
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

# 🔧 Dar permisos de ejecución al Gradle Wrapper
RUN chmod +x gradlew

# Descargar dependencias antes de copiar el código fuente (mejor cacheo)
RUN ./gradlew dependencies --no-daemon || return 0

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto y generar el JAR
RUN ./gradlew clean bootJar --no-daemon -x test

# ======================================================
# 2️⃣ Etapa de ejecución (imagen liviana)
# ======================================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar el JAR generado desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Variables de entorno por defecto (pueden ser sobreescritas en DigitalOcean)
ENV SERVER_PORT=8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]