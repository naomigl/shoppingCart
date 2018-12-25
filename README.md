Shopping Cart
=============

An executable for calculating the total price of a given cart.

How to run
----------

The program can be executed with the jar file, using the java command:

```
java -jar shoppingCart.jar product1 product 2 ... pproductN
```

How to build
------------

The project was built using the Scala Build Tool (SBT):

* [SBT](https://www.scala-sbt.org/)

To install the dependencies and run the tests, place yourself in the project folder and execute:

```
sbt test
```

To create an executable jar, place yourself in the project folder and execute:

```
sbt assembly
```

The program was written using the Scala programming language (v. 2.12.8):

* [Scala](https://www.scala-lang.org/)


Assumptions
-----------

The following assumptions have been made when designing the solution:

1) The number of each product passed as an argument to the program will be the number of times the product name is passed.
2) All discounts are available at the time the program is executed. There is no expiration date to any product nor discounts.
3) A product must have a price and a tax percentage in order to be valid.

Solution description
--------------------
The program follows the next steps:

1) Parses the products given as arguments, grouping them when equal in order to calculate the number of each of them.
2) Converts each productName into a Valid or UnregisteredProduct with its own price and tax percentage.
2) Calculates all intermediate steps of the bill (subtotal, discounts, base, taxes, total) and prints them.
3) Prints all products that have not been taken into account when calculating the bill.

What could be improved
----------------------
 - Product information and its retrieval could be stored in a database, or at least be changed easily
 (right now there is no other way to add a product or change its price but to change it in code).
 - Discounts could be as well consulted via API in some other module, so that each part of the solution fulfill 
 the single responsibility principle.
 - Code could be more testable using classes for private data, and traits that could be mocked in tests for 
 ProductDatabase and DiscountDatabase. 