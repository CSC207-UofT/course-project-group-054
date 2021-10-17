# Group Report
 
> We make transactions almost every single day. It is therefore important to keep track of our money. Our project revolves around solving exactly this problem. We have created a money management solution which allows users to track their expenses, split them with other people, and even create groups. 

## Specification Summary
We designed our app such that each class is responsible for a specific task. Our data (for Phase 0 only) is stored in the `Data` class in the form of objects. Our app models real people through the `People` class, while registered users are created and stored as `User` objects.

The `User` class is a child class of `People`. Further, the `Expense` class models users’ expenses. Similarly, the `Group` class models groups of people.

## Design Summary
- Our app is designed in a way to minimise redundant storage of data. We use **unique identifiers** for expenes, users, and groups. This allows us to keep a single copy of all objects (of all types) in our central database. Then any object can be accessed and even modified by using its unique identifier.

## Member progress
- **Rohan** has mostly worked on the `User`, `Expense`, `Controller`, and `View` classes. He worked with other team members on implementing features such as creating a group, adding an expense (group and non-group), creating a user, and logging in and authenticating the user.
- **Wasee** has worked on `Controller`, `Person`, `User`, `Group`, and `View` classes. He has also worked on the CRC Cards and specification, as well. He has implemented features such as payDebt and refined features and documentation. 
- **Subhasis** has worked on `Budget`, `Item`, `InOut`, and separated `Controller` from the `View`. He created the Budget test cases. He has worked on the CRC Cards and Specification as well.

## Plans for Phase 1 & Phase 2
- **Storing data in actual database**: For Phase 0, we created a `Data` class to emulate a database. We did this because setting up a database requires careful consideration of what kind of database to use (SQL, noSQL, Graph, on-device storage etc.). We plan to implement a working database cloud or on-device database for next submissions.
- **Refining our design**: We learnt a lot about design challenges while working on Phase 0 and we plan to make our design better for next submissions.
