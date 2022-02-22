FROM openjdk:8
LABEL key="com.api.parking.control.api"
VOLUME /var/files
EXPOSE 8080
ARG JAR_FILE=target/*.jar
ADD target/parking-control-api.jar parking-control-api.jar
COPY ${JAR_FILE} parking-control-api.jar
ENTRYPOINT [ "java", "-jar", "parking-control-api.jar" ]