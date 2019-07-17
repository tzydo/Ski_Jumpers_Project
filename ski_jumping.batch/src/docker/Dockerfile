FROM openjdk:8
ADD ski_jumping.batch.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "app.jar"]