# Pokedex
A small [Pokedex](https://www.pokemon.com/us/pokedex) Rest API that serves data to a web application providing a list of Pokemons and their corresponding details.

In this project I have tried to follow DDD and hexagonal architecture. Note that I have decided to place the repository 
interface in the domain, based on the idea that the repository is a fundamental part of the domain and that its 
interface is a contract that defines the data persistence operations necessary to fulfil the domain requirements. 
However, if we wanted to follow ports and adapters, we could define the interface in the application layer, 
in the output ports package, and in the infrastructure we could define the adapter within the repository package.

I am using Java 17 and Spring Boot 3.1.1 to develop the project.

## How To Start
Executing `docker compose up` a mysql and the application will be up with a few pokemons migrated with 
[Flyway](https://flywaydb.org/documentation/database/mysql#java-usage).

There is a [Swagger](https://swagger.io/) at `http://localhost:8080/swagger-ui.html`.

## Next Steps
- As a first approach, since the database has a few pokemons, I am not using pagination. It would be convenient to use 
pagination with a larger database, in order to avoid the Spring Data findAll anti-pattern we are using by returning all 
pokemons.
- We could put some endpoints together, such as `/pokemons` and `/pokemons/search`, but in order to keep it simple, 
and also to provide greater clarity and separation of responsibilities, I have opted for this option first, considering 
the most important thing that the API is easy to understand, use and maintain. Putting it together would not avoid 
this, but it would need to be properly documented how it should be used, and more so if I could grow the filters in the 
future.