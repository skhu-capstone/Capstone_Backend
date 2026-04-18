FROM amazoncorretto:21
ARG JAR_FILE=build/libs/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo 'Asia/Seoul' > /etc/timezone
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
