# todo-app-docker
this is one spring boot application along with the docker file.
In this application we enabled swager.
here we have one controller for the task entity along with curd operations.
this application we can build using gradle and create image and deploy into docker container by using below comands.

# this command we can use to build a jar file along with all the dependencies.
./gradlew bootJar
# this command we can use to build a docker image.
docker build -t to-do-app-image .
# this comand we can use to see wether image is creaded in docker or not successfuly.
docker images
# this comand we can use to run image in docker container.
docker run -d -p 8080:8080 to-do-app-image:latest

