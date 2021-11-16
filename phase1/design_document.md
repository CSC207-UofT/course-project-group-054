# Design Document

## Your updated specification

A program that enables users to create groups, split expenses with one another in groups, and set budgets. There are two kinds of users: users that have an account, and users that do not have an account. Every user has a balance, a name, an email address, and a mapping from other users to the amounts of money owed by this user to the other users. Only users that have an account have a unique identifier and a profile that they can edit and can create a group. Users without an account are placeholder user objects that users with accounts can create in order to track expenses incurred by people who do not have an account.

**Groups organize users so that users can share expenses with others in the group. Each expense is associated with a cost, date, a mapping between users and the amounts of money owed by those users, and notes. Each group contains a list of users and a list of expenses associated with the group. Each group is made up of at least one user with an account. Each expense contains hashmaps of people to amounts that were borrowed _or_ amounts that were lent. Through this Users can pay off the money they borrowed using their balance.** A user with an account can add another user with an account to a group. Any user in a group that has an account can remove itself or any other user from the group and add an expense to the group. Users with accounts can add users without accounts to the group as placeholders representing people without an account to account for amounts of money owed by those people. Any user in a group that does not have an account cannot add expenses to or remove any user, including itself, from a group.

**Groups also have budgets. A budget stores a list of items, each of which has a name, cost and quantity. A budget has a spending limit: the maximum amount of money that can be spent on items in the budget. The items in a budget can be converted into expenses that can be added to the group that contains the budget.** 

**Changes**

In phase zero, the ability to store who borrowed/lent what amount was not saved, however now it is. 

In phase zero, our app relied solely on the command line. Since then, we have made progress regarding the front end of our app using Spring, HTML, CSS, and Javascript.

## OPTIONAL: Diagram of Code

## A description of any major design decisions your group has made (along with brief explanations of why you made them).

In our original specification, budgets were associated with users, but they are now associated with groups instead because the items in budgets can be converted into expenses, and associating budgets with groups allows those expenses to be shared with others in the group.

In phase zero, the items in a budget were grouped into categories, but this is no longer the case in phase one. The purpose of categories was to organize items into subgroups within a budget. Since a group can have multiple budgets, a user can add items to multiple budgets instead of multiple categories in the same budget, so categories did not provide much unique functionality.

We have decided to keep the command line interface _and_ the web interface, both for ease of rough testing ease of use for the target user.

We decided to use Spring for our web application, and have created new files and refactored our files and folders to adhere to the Web app design requirements.

We use dependency injection and annotations to link our entities and repositories together. This tells Spring what the various classes in our project are meant for. We are using a Postgresql database for storing data. We have tested this locally on our devices and also included a `.sql` file that contains code to set up the database. One the database has been set up, we can run our Spring application and find our server running on port 8080 by default. We use the `/api` path for API requests and all out static front-end (HTML, JavaScript, CSS, etc.) files will be stores in `src/main/resources/public` folder in our project. This is a special folder that Spring uses to server static content by default. Once our server is running, the client can visit our web app and the front-end will interact with the backend, which, in turn, will interact with our central database to fetch/create/modify the data and send a _response_ back to the front-end.

## A brief description of how your project adheres to Clean Architecture (if you notice a violation and aren't sure how to fix it, talk about that too!)

The frameworks and drivers classes, such as the command line or web app, pass in information to our controllers. Our interface adapters do not depend on the views directly; instead, they depend on InOut interfaces that are in the interface adapter layer and that the views implement. The controllers in the interface adapters layer convert data from the view into a format that use cases can use, and pass the converted data to the use cases, which work on the entities to perform the appropriate actions. The use cases also interact with gateway interfaces in the use cases layer that database gateway classes in the interface adapter layer implement so that the use cases do not depend on the interface adapter layer or the database itself.

There are still a few cases where classes in layers use other classes in layers that are not adjacent; for example, our controllers sometimes use entities. We plan to fix this violation by adding new use case classes, such as a CurrentUserManager use case that stores the current user so that the controller does not have to store it.

We have not yet created InputBoundary or OutputBoundary interfaces that the use cases or presenters, respectively, implement. We have not yet separated out the controllers from the presenters; that is a task we intend to complete in Phase 2.

## A brief description of how your project is consistent with the SOLID design principles (if you notice a violation and aren't sure how to fix it, talk about that too!)

S - Single Responsibility Principle: All classes in our project have a single responsibility. Whether it's managing the entities(use cases), displaying the command line (View), or translating the instructions from the command line (Controller) they all have one explicit purpose.

O - Open/Closed Principle: Entities can be modified to include more attributes, such as storing more user information, however modification of our entities is limited due to our use cases depending on different aspects of them.

L - Liskov Substitution Principle: We do not have many subclasses, however the few that we do have don't violate this principle given that we change the usages. For instance replacing all instances of Person with User will not break the program.

I - Interface Segregation: The interfaces that we do have don't possess a large amount of functions. They are all relatively small. The largest one we have is inOut which deals with input and output of information to our interface.

D - Dependency Inversion: A future example of this principle would be with our presenter. Our use cases would rely on an output boundary and our presenter would have to implement this boundary.

## A brief description of which packaging strategies you considered, which you decided to use, and why. (see slide 7 from the packages slides)

The main packaging strategies we discussed are "By Layer" and "By Feature". We decided to choose the by layer strategy because we already had a close implementation of it from Phase 0, and we felt it was the most clear for our program. Since many of our features work in tandem, we realized that grouping things by features would be difficult and suboptimal for this program. To a lesser extent we considered the "Inside/Outside" pattern because one can see creating and managing expenses as orders however we thought that the large amount of methods we would need would turn the classes into bloaters.

## A summary of any design patterns your group has implemented (or plans to implement).

We implemented the Observer design pattern for Budget and Item because a Budget object needs to be aware of changes to the attributes of the Items it stores to satisfy certain constraints (for example, preventing an increase to an Item's cost or quantity that would result in its Budget's spending limit being exceeded). The Budget is the Observer (implementing the VetoableChangeListener and PropertyChangeListener interfaces) and the Items are the Observables (having VetoableChangeSupport and PropertyChangeSupport fields that allow an Item to notify its Observer). When one of the attributes of an Item is changed, either the firePropertyChange or the fireVetoableChange method is called to notify the Budget, which then takes an action in the vetoableChange or propertyChange method.

## A progress report (open questions your group is struggling with, what has worked well so far with your design, a summary of what each group member has been working on and plans to work on next)

### Open questions

- Is there any alternative to having a switch statement in Budget's vetoableChange method? If not, is it a code smell in this context? (Note: we will implement the advice we received in Phase 2.)

### What has worked well so far with our design

- Our classes are open for extension and closed for modifications. We can easily add on functionality but fundamentally changing a class may prove difficult.
- Our design is primed for an extension into a strong integration with a database.
- Our design allows us to test different aspects of our code more easily. (Since it is well organized)

### Member progress and plans

- **Rohan** has worked on the backend. Mainly he worked with the Spring Boot Framework and Postgresql to implement a RESTful API with a PSQL database for storing and retrieving data. He set up the database and added schema for user, groups, and expenses tables. He used JPA and JDBC to make SQL queries from within Spring Boot.
- **Wasee** has worked on completing the implementation of various features most notably expenses. Wasee has also helped make the project adhere to clean architecture by refactoring many methods. Wasee has answered many questions for the design document. He plans on making a friends system once the database is fully operational.
- **Subhasis** has worked on integrating the Budget and Item classes into the rest of the program by means of use cases and controllers and on refactoring for making the program adhere to Clean Architecture. He also contributed to the design document. He plans to work on enabling the front end to use the Budget use cases.
- **Lingyun** has worked on completing the implementation of the features on updating the profiles of the user, and managing the groups in the Controller, and use cases (GroupManager, UserManager). Also, he helps to make the program adhere to Clean Architecture by refactoring methods/names.
- **Mohamed** has worked on the frontend. He has also worked on writing some test code. He plans on integrating the frontend with the backend.
