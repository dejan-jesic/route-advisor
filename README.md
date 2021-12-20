# Route advisor

Route advisor java task

## Task

Create a simple Spring Boot service, that is able to calculate any possible land route from one country to another. 
The objective is to take a list of country data in JSON format and calculate the route by utilizing individual 
countries border information.

### Countries data source

https://raw.githubusercontent.com/mledoze/countries/master/countries.json

### Environment & Dependencies

* Java Version 11
* Lombok
* Spring
* Springdoc
* Flyway
* Postgres
* JPA
* JUnit
* Mockito
* Maven
* Docker
* Docker-compose
* Shell script

## Setup & Run

* Navigate to the root and run `./mvnw clean install`. Mentioned command will run complete lifecycle which includes 
  unit and integration tests
* From the root you can run `sh start.sh` which will run docker-compose and start docker containers
* After approx 20 seconds you can run `sh test-fastest-route.sh` with parameters 
  (e.g. `sh test-fastest-route.sh GRC ESP`). First parameter represents origin country code and second one represents 
  destination country code. If you don't provide parameters default ones will be used 
  (`origin = SRB` and `destination = DEU`) 
* At the end don't forget to stop all containers with `sh stop.sh`

### Swagger

Location `<HOST>/swagger-ui.html`

e.g. `http://localhost:8080/swagger-ui.html`


### The fastest route solution

The entire algorithm for the fastest route search is performed using Dijkstra algorithm.

## Developer comments

* I decided to use Java 11 since I have Java 11 installed on my machine, although I really like latest technologies! 
* App is covered with some unit and integration tests, I added them just to give you an insight how I write them, 
  but due to time constraints it is not as detailed and in depth as I usually prefer.
* Security was not a requirement, so I didn't set Spring Security.
* Code written with the highest emphasis to readability and maintainability since it is a challenge that is going to be reviewed by a person :)
* I decided to "change" requirement since I believe that using parameters through URL is a bad practice,
  it may confuse developers and Dispatcher Servlet when API is defined with the same number of routes 
  (e.g. 1: `/api/{param1}` 2: `/api/resource`). That's why I was using request parameters.
* Initialization of countries that are fetched from the external API is not optimized at its best. It can be improved, 
  but due to time constraints and considering the fact that it's performing on startup and not in the runtime,
  I decided to leave as is.
