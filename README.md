# SIR_TP6
## JHipster = AngularJS + JPA + SpringBoot

### Application by [LAGADEC Quentin](https://github.com/quentin29200) et [GIGOU Nicolas](https://github.com/gigouni)
Application joining the result of two labs during the SIR. This is the connection between a FRONT AngularJS (Javascript) and BACK with JPA (Java) around an electronic devices manager.

The aim of this lab is to create an user interface which use at least one reading service (GET request) and one writing service (POST/PUT/PATCH request) based on a [previous JPA project](https://github.com/quentin29200/SR1_testjpa). So this step will be divided in two sub-steps, the FRONT-END part in one hand and the BACK-END in the other hand.

To be more efficient and use the tools seen during courses, we've used JHipster. Why ? Because JHipster is an Yeoman generator (like AngularJS generator). The database is based on the JPA principles and there is a separation between our user interface and our services application. More over, it's using SpringBoot and Swagger. This last one provides a REST API.

We've followed the next model to build our project : 

![model](https://github.com/gigouni/SIR_TP6_DeviceManager-JHipster/blob/master/sir_tp6_jhipster_model.png?raw=true"The model of the application")

## Run

To run the application, run the command

Before you can build this project, you must install and configure the following dependencies on your machine

    mvn

And then go on your browser to [http://127.0.0.1:8080/](http://127.0.0.1:8080/)
