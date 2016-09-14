# tsys-logiweb
T-systems school project

## Prerequisites

 * MySQL 5.0+
 * Tomcat 8+
  
## Installation

 1. Clone project 
 2. Populate MySQL database with this [dump 15-Sep-2016](https://gist.githubusercontent.com/lislon/a30b739e5037e9d93b656a710d59b3a8/raw/c6d56d396794ee2632b55027760b4d81a336e6d0/gistfile1.txt). 
 3. Go to project source and execute 2 commands:
 
 `mvn install`
 
 `mvn install -pl api tomcat7:deploy `

Then proceed to http://localhost:8080/logiweb-api/




 

