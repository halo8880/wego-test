## Design considerstion
### FR:
Find the nearest availble carparks for the given coordinate

API supports pagination

return 400 to requests without latitude and longtitude
### NFR:
Scalable:  We should add or remove the servers as needed with minimal to no disruption to the service availability

Performance
### Codebase
There is nothing complex with this project, so any framework with support for creating web APIs, connecting DB and caching would be enough

So I used spring-boot along with hibernate and lombok, as those are my familiar tech tools
### API:
#### GET /carparks/nearest
params: latitude, longtitude, page, per_page

flow: start >> read from cache/extract data from CSV file >>
(data from reading the CSV will be cached using spring cache, so the first call will be slow, after that, the data will be got1ten from cache)

at this step we can either decide to read availability data from DB or call the availability function and return

 1. read availability data from DB/cache: this will reduce latency but sacrifice the consistence of data returned, and required the batch_availability to be scheduled right

 2. call the batch_availability to get the realtime data, the data will be consistent, but the risks is that we are calling to the 3rd party API, which may cause un-predictable latency and errors

as we are currently don't have the schedule to call batch_availablity frequently, so we cannot count on the saved data from DB/cache, so I choose 2nd method

 + the endpoint accepts SVY21 format, to change this to another is simple, but requires more time

#### PATCH /carparks/batch-availability
flow: start >> call API to get list available carparks at that time >> save to DB

consider saving this to a cache server, and base on the realworld problem to set the suitable TTL for the cache

## Database
carpark_availability: id, timestamp

carpark_data: id, carpark_number, carpark_availability_id

carpark_info: id, lot_type, lots_available, total_lots, carpark_data_id

## Scalability
the data of availability can grow up very large, and to be easier to scale out, I would put it in NoSQL db, and later on, we can do sharding based on the area

I feel more comfortable with Postgres, so I used it in this project

Realworld deployment: instead of putting the database credentials inside the application.properties and docker-compose.yml, we should separate them to another .env file, and inject the environment variables using the CI/CD tool

# Run the app
The app was compiled using Java 17, so please make sure Java 17 is in you environment, or you can go to pom.xml and change the Java version (hopefully it will work with java 8+)

##### ./mvnw clean package or mvn clean package

##### docker-compose up -d postgres

(wait until it's finished initialize the postgres then:)

##### docker-compose up app
application will be running on port 8080

database connection:

postgreSQL

url=localhost:5432/wego

username=postgres

password=postgres

# Testing
I believe that testing is a very important phase of software development

And unit test/integration test is also important to ensure the later changes not to break the old code flow, if I had time, I would choose JUnit4, along with Mockito to do the unit test.

We can discuss about Testing if we have time later on.