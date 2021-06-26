Publisher management API provides tools to manage publishers and books they offer

Endpoints

GET    /publishers                     - get all publishers
GET    /publishers/{name}              - get single publisher
PUT    /publishers/{name}              - create or update publisher
DELETE /publishers/{name}              - delete publisher and all its books

GET    /publishers/{name}/books        - get all books of publisher (with pagination)
POST   /publishers/{name}/books        - add new book for publisher
DELETE /publishers/{name}/books/{isbn} - delete book from publisher


GET    /books                          - get all books
GET    /books?author={author}          - find books by filters (author, title...)
GET    /books/{isbn}                   - get book by isbn
DELETE /books/{isbn}                   - delete book
PUT    /books/{isbn}                   - update book
