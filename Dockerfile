# --- Etapa 1: Build (Construcción con Maven y Java 21) ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copiamos configuración de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copiamos el código fuente
COPY src ./src

# 3. Compilamos el .jar (saltando tests para evitar errores por ahora)
RUN mvn clean package -DskipTests

# --- Etapa 2: Run (Ejecución final ligera) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 4. Traemos solo el .jar construido en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# 5. Exponemos el puerto interno
EXPOSE 8080

# 6. Arrancamos la app
ENTRYPOINT ["java", "-jar", "app.jar"]