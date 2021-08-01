Publisher Management API
========================
API provides functionality to manage publishers and books they offer

Endpoints
--------

| Method | URL                               | Description                        |
|--------|-----------------------------------|------------------------------------|
| GET    | /publishers                       | get all publishers                 |
| POST   | /publishers                       | create publisher                   |
| GET    | /publishers/{id}                  | get single publisher               |
| PUT    | /publishers/{id}                  | update publisher                   |
| DELETE | /publishers/{id}                  | delete publisher and all its books |
| GET    | /publishers/{id}/books            | get all books of publisher         |
| POST   | /publishers/{id}/books            | add new book for publisher         |
| PUT    | /publishers/{id}/books/{isbn}     | update book from publisher         |
| DELETE | /publishers/{id}/books/{isbn}     | delete book from publisher         |
| GET    | /books                            | get all books                      |
| GET    | /books/{isbn}                     | get book by isbn                   |

TODOs
----------

* business logic:
    * add use cases for books
    * delete all publisher's books on its deletion
    * add validation:
        * return error message if validation failed
        * email address must be valid
        * publisher name must be unique
        * every book must belong to single publisher
    * implement persistence layer:
        * JPA repositories for RDB
        * create sql scripts for initial data population
        * modification (create, update, delete) should be transactional
        * modification (create, update, delete) should use optimistic locking
* features:
    * add pagination for endpoints returning collection of resources (GET /publisher, GET /books...)
    * add filters for endpoints returning collection of resources (GET /publisher, GET /books...)
    * move swagger config constants to yaml file
    * security with JWT
    * hateoas