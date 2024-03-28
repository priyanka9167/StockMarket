FROM amazoncorretto:19
WORKDIR /app
COPY  build/libs/*.jar ./app.jar
EXPOSE 8080
CMD [ "java","-jar","./app.jar" ]
