FROM maven

WORKDIR "/app"
ADD . /app
RUN mvn clean package install

ENTRYPOINT ["java", "-jar", "target/matrix-backend-api-0.0.1-SNAPSHOT.jar"]