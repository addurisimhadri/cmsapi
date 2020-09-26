FROM openjdk:8
ADD target/wicmsapi.jar wicmsapi.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","wicmsapi.jar"]