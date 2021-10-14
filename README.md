# Order-Management
An application for processing client orders for a warehouse. Relational 
databases are used to store the products, the clients and the orders. The application is
structured in packages using a layered architecture.

Reflection techniques to create a generic class that contains the methods for 
accessing the DB: create object, edit object, delete object and find object. The 
queries for accessing the DB for a specific object that corresponds to a table are
generated dynamically through reflection.
