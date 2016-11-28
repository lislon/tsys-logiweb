# T-systems Java School project

Web application for modeling logistics company. 

[Complete task (Russian)](docs/task.md)

## Screenshot

![Demo page](/docs/edit-order.png "Demo page")

## Prerequisites

 * MySQL 5.0+
 * Tomcat 8+ / Wildfly 10.0+
  
## Installation

 1. Install prerequisites
 2. Clone project 
 3. Populate MySQL database with this [dump 15-Sep-2016](https://gist.github.com/261ea8ed768aa63346a0ba294ad44923).

## Deployment 

### Tomcat deployment guide:

add to your tomcat /conf/tomcat-users.xml:
    
```xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<role rolename="manager"/>
<role rolename="admin"/>
<user username="admin" password="admin" roles="admin,manager,manager-script,manager-gui"/>
```

  1. Go to source root of project
  2. run 
  
 `mvn install -pl api tomcat7:deploy `

Then proceed to http://localhost:8080/logiweb/

### Wildfly deployment guide:
Start your Wildfly server. 
Run `mvn wildfly:deploy`

Visit http://localhost:8080/logiweb/ 
 

## Login page

Default credentials: 

User: admin@test.ru

Pass: admin@test.ru

