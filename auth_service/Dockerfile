####-----Stage 1: Build---------
FROM  maven:3.9.6-eclipse-temurin-17 as builder
WORKDIR /build
#Copy pom first dependency caching
COPY pom.xml .
RUN maven dependency:go offline
#Copy source code
COPY src  ./src
##---== Stage2 : Runtime ---
FROM eclipse-temurin:117-jre-jammy
#create non root user (security best practice)

RUN useradd -r -u 1001 appuser
WORKDIR /app

# Copy only the built jar from builder stage
COPY --from=builder /build/target/security_service-0.0.1-SNAPSHOT.jar app.jar

# Change ownership
RUN chown -R appuser:appuser /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["java",
  "-XX:+UseContainerSupport",
  "-XX:MaxRAMPercentage=75.0",
  "-XX:InitialRAMPercentage=50.0",
  "-jar", "/app/app.jar"]