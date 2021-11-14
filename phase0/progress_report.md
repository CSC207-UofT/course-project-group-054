# Group Report

> We make transactions almost every single day. It is therefore important to keep track of our money. Our project revolves around solving exactly this problem. We have created a money management solution which allows users to track their expenses, split them with other people, and even create groups.

## Specification Summary
We designed our app such that each class is responsible for a specific task. Our data (for Phase 0 only) is stored in the `Data` class in the form of objects. Our app models real people through the `People` class, while registered users are created and stored as `User` objects.

The `User` class is a child class of `People`. Further, the `Expense` class models users’ expenses. Similarly, the `Group` class models groups of people.

## CRC Cards Summary
We have **6** main classes: Person, User, Expense, Group, Controller, and View.
- **`Person`**: The Person class models a person. It contains attributes such as name. Person class is the _parent class_ of `User`.
- **`User`**: User class models a registered user on our app. It is a child class of `Person` and inherits some attributes and methods from its parent class. It also contains attributes UUID, email, balance, expenses, and groups. It contains many methods such as updateBalance() among others.
- **`Expense`**: Expense class models an expense. It contains attributes EUID, amount, people, isGroupExpense. It also contains methods toString() among others. An expense can either be a group expense (iff isGroupExpense is true) or a non-group expense.
- **`Group`**: The Group class models a group of people. Group class contains attributes like GUID, people, etc.
- **`Controller`** class is responsible for handling the data and overall structure of the program. It contains many helper methods used to fetch data from the database.
- **`View`** class contains all the views/interface the user interacts with. For example, the login command-line interface is a separate method in the View class.

## Scenario Walkthrough Summary

The scenario walkthrough involves creating an account, creating a new group, viewing groups, creating a new expense, checking expenses, checking the balance, and logging out.

## Skeleton Program Summary

The skeleton program consists of 13 classes and 2 interfaces in 5 packages:
- **controller**: Controller, InOut (interface)
- **data**: Data
- **entities**: AccountFeatures (interface), Budget, Expense, Group, Item, Person, User
- **use_cases**: ExpenseManager, GroupManager, UserManager
- **view**: Main, View

## Member progress
- **Rohan** has mostly worked on the `User`, `Expense`, `Controller`, and `View` classes. He worked with other team members on implementing features such as creating a group, adding an expense (group and non-group), creating a user, and logging in and authenticating the user. He has also helped debug non functional code.
- **Wasee** has worked on `Controller`, `Person`, `User`, `Group`, and `View` classes. He has also worked on the CRC Cards and specification, as well. He has implemented features such as payDebt and refined features and documentation. He has also helped debug non functional code.
- **Subhasis** has worked on `Budget`, `Item`, `InOut`, and separated `Controller` from the `View`. He created the Budget test cases. He has worked on the CRC Cards and Specification as well. He has also helped debug non functional code.
- **Lingyun** has worked on `Groups`, `Expense`, created tests for entities the later deleted `ExtendedGroup` and `TemporaryGroup`. Lingyun has also worked on the CRC Cards and specification. He has also helped debug non functional code.
- **Mohamed** has worked on the CRC cards and specification. He has also helped with naming.

## Design Summary & Questions
- Our app is designed in a way to minimise redundant storage of data. We use **unique identifiers** for expenes, users, and groups. This allows us to keep a single copy of all objects (of all types) in our central database. Then any object can be accessed and even modified by using its unique identifier.
- We would like help on how to create an Android app and extend our program to have a GUI. Also, we would like to get suggestions on how we should store our data and/or which database we should use. Any design feedback would also be of great help.

## Plans for Phase 1 & Phase 2
- **Storing data in actual database**: For Phase 0, we created a `Data` class to emulate a database. We did this because setting up a database requires careful consideration of what kind of database to use (SQL, noSQL, Graph, on-device storage etc.). We plan to implement a working database cloud or on-device database for next submissions.
- **Refining our design**: We learnt a lot about design challenges while working on Phase 0 and we plan to make our design better for next submissions.
