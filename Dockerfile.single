# syntax=docker/dockerfile:1

# FROM eclipse-temurin:17-jdk-jammy
FROM gradle:7.6.2-jdk17-jammy

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . ./

RUN gradle build --no-daemon 

CMD ["gradle", "bootRun"]