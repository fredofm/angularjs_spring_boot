# angularjs_spring_boot
- Frontend (angularjs + gulp + bower)
- Backend (Spring Boot + RestFull HATEOAS + LiquidBase)

## Backend

This is a RestFull HATEOAS example API build with SpringBoot. This example use SpringSecurity for authorization and authentication porpuse based on JWT (JSON Web Token) and liquidBase for BBDD scripts management.

A gradle installation is embebed so you can run the project with *gradlew run* command. This command build a fat jar with a embebed Tomcat, so it's self served in this container.

## Frontend

It's a simple client side interface build with AngularJS. It's for learning porpuse so you can inspect how authentication is made, and how you must connect and query a Rest service.

You have to install gulp and bower in order to run this project.

You must run *bower install* in the frontend root directory to download all required dependencies, and run *gulp* command to serve the interface.