FROM openjdk:11
ADD target/*.jar visitation-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "visitation-tracker.jar"]