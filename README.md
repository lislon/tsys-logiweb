# tsys-logiweb
T-systems school project

## Prerequisites

 * MySQL 5.0+
 * Tomcat 8+
  
## Installation

 1. Clone project 
 2. Populate MySQL database with this [dump 15-Sep-2016](https://gist.github.com/261ea8ed768aa63346a0ba294ad44923).
 3. Go to project source and execute 2 commands:
 
 `mvn install`
 
 `mvn install -pl api tomcat7:deploy `

Then proceed to http://localhost:8080/logiweb-api/




 

