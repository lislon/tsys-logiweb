# tsys-logiweb
T-systems school project

## Prerequisites

 * MySQL 5.0+
 * Tomcat 8+
  
## Installation

 1. Clone project 
 2. Populate MySQL database with this [dump](https://gist.github.com/8b73b9e1ae8a996792a2ddf22da51665). 
 3. Go to project source and execute 2 commands:
 
 `mvn install`
 
 `mvn install -pl api tomcat7:deploy `

Then proceed to http://localhost:8080/logiweb-api/




 

