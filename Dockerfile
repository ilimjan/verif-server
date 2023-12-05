FROM openjdk:8-jdk-alpine
ADD target/PpkVerificationServer.war app.war
ENTRYPOINT ["java","-jar","/app.war", "--spring.profiles.active=production"]
