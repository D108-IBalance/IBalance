FROM openjdk:21

ARG JAR_FILE=build/libs/*.jar
ARG JASYPT_KEY

ENV JASYPT_KEY=$JASYPT_KEY

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-DJASYPT_KEY=${JASYPT_KEY}", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]