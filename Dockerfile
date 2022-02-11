FROM openjdk:8
LABEL key="parking-control-api-michelli-brito"
EXPOSE 8080
ADD target/parking-control-api.jar parking-control-api.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} parking-control-api.jar
RUN mvn clean install
CMD [ "java", "-jar", "parking-control-api.jar" ]