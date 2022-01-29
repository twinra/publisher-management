Publisher Management API
========================
API provides functionality to manage publishers and books they offer

Requirements
------------
* Manage publishers:
  * operations
    * create (name and contact information) +
    * read (all or by name) +
    * update contact information +
    * delete +
* Manage publishers advanced:
  * operations
    * deactivation aka logical deletion +
  * validation
    * publisher name should be unique on creation
    * publisher should be inactive on deletion
* Update publisher's assortment:
  * bulk update/upload of books offered by publisher
    * add new books
    * deactivate old books, not offered by publisher anymore
    * keep remaining books
    * every book should belong to only 1 publisher
  * remove books from publisher's assortment - to correct potential mistakes
* Search books
  * by different criteria:
    * ISBN
    * publisher
    * title
    * author
    * activity flag
* Fine-grained publisher contact information:
  * fields:
    * email
    * phone number
    * address
  * validation for everything
* Manage store assortment
  * mark books to show in the store catalog
    * should be possible with bulk upload and individual updates
  * extend search with another criteria:
    * by available-for-sales flag
* Manage store stock
  * keep track of available stock per book
  * increase stock for new arrivals and decrease for sales
  * extend search with another criteria:
    * by on-stock flag
* Authorisation
  * introduce roles
    * admin  - full access
    * editor - full access except deletion
    * viewer - readonly access

Non-functional requirements
-----
* Hexagonal architecture:
  * decouple business logic from upstream (REST, Queues...) and downstream (persistence layer)
* Thorough but pragmatic test coverage - with focus on functionalities and not implementation details
* Endpoints:
  * add pagination for endpoints returning collection of resources (GET /publisher, GET /books...)
  * add filters for endpoints returning collection of resources (GET /publisher, GET /books...)
  * move swagger config constants to yaml file
  * security with JWT
* Persistence layer:
  * JPA repositories for RDB
  * create sql scripts for initial data population
  * modification (create, update, delete) should be transactional
  * modification (create, update, delete) should use optimistic locking