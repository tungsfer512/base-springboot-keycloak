FROM openjdk:17.0.2-bullseye

ARG POSTGRES_HOST=db
ARG POSTGRES_USER=postgres
ARG POSTGRES_PASSWORD=postgres
ARG POSTGRES_DB=postgres
ARG SERVER_INTERNAL_PORT=8080
ARG SERVER_MOUNT_POINT=app

WORKDIR /${SERVER_MOUNT_POINT}

RUN apt update
RUN apt upgrade -y
RUN apt install -y sshpass sudo nano

COPY . .

RUN ./mvnw clean install -DskipTests -q

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]