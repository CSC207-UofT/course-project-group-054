# Design Document

## Specification

A program that enables users to create groups, split expenses with one another in groups, and set budgets. There are two kinds of users: users that have an account, and users that do not have an account. Every user has a balance, a name, an email address, and a mapping from other users to the amounts of money owed by this user to the other users. Only users that have an account have a unique identifier and a profile that they can edit and can create a group. Users without an account are placeholder user objects that users with accounts can create in order to track expenses incurred by people who do not have an account.

Groups organize users so that users can share expenses with others in the group. Each expense is associated with a cost, date, a mapping between users and the amounts of money owed by those users, and notes. Each group contains a list of users and a list of expenses associated with the group. Each group is made up of at least one user with an account. Each expense has mappings from people to amounts that were borrowed _or_ amounts that were lent. Users can thus pay off the money they borrowed using their balance. A user with an account can add another user with an account to a group. Any user in a group that has an account can remove itself or any other user from the group and add an expense to the group. Users with accounts can add users without accounts to the group as placeholders representing people without an account to account for amounts of money owed by those people. Any user in a group that does not have an account cannot add expenses to or remove any user, including itself, from a group.

Groups also have budgets. A budget stores a list of items, each of which has a name, cost and quantity. A budget has a spending limit: the maximum amount of money that can be spent on items in the budget. The items in a budget can be converted into expenses that can be added to the group that contains the budget.

### Changes

- In Phase 0, the ability to store who borrowed/lent what amount was not saved, but now it is.

- In Phase 0, our app relied solely on the command line. Since then, we have made progress regarding the front end of our app using Spring, HTML, CSS, and Javascript.

## Major design decisions

- In Phase 0, budgets were associated with users, but they are now associated with groups instead because the items in budgets can be converted into expenses, and associating budgets with groups allows those expenses to be shared with others in the group.

- In Phase 0, the items in a budget were grouped into categories, but this is no longer the case in Phase 1. The purpose of categories was to organize items into subgroups within a budget. Since a group can have multiple budgets, a user can add items to multiple budgets instead of multiple categories in the same budget, so categories did not provide much unique functionality.

- We decided to keep the command line interface as well as add a web interface, both for ease of rough testing and for ease of use for the target user.

- We decided to use Spring for our web application, and have created new files and refactored our files and folders to adhere to the Web app design requirements.

## How our project adheres to Clean Architecture

The frameworks and drivers classes, such as the command line or web app, pass in information to our controllers. Our interface adapters do not depend on the view directly; instead, they depend on `InOut` interfaces that are in the interface adapter layer and that the view implements. The controllers in the interface adapters layer convert data from the view into a format that use cases can use, and pass the converted data to the use cases, which work on the entities to perform the appropriate actions. The use cases also interact with gateway interfaces in the use cases layer that database gateway classes in the interface adapter layer implement so that the use cases do not depend on the interface adapter layer or the database itself.

Since Phase 1, we have been fixing cases where classes in layers used other classes in layers that are not adjacent; for example, our controllers sometimes used entities. We fixed this violation by adding new use case classes, such as a `CurrentUserManager` use case that stores the current user so that the controller does not have to store it.

## How our project is consistent with the SOLID design principles

S - Single Responsibility Principle: The classes in our project generally have a single responsibility. Whether it's managing the entities (use cases), displaying the command line (view), or translating the instructions from the command line (controllers), they have a single explicit purpose.

O - Open/Closed Principle: Classes in our program are open for extension and closed for modification since they can be subclassed to add additional features without having to change existing code. For example, we can extend our program to use a different kind of database without having to modify use case classes because those use case classes depend not on the repositories but on interfaces that gateways to the databases implement. A gateway for a different kind of database can be injected into the use case classes.

L - Liskov Substitution Principle: We do not have many subclasses, however the few that we do have do not violate this principle if subclass objects are substituted for superclass objects. For instance, replacing all instances of `Person` with `User` will not break the program.

I - Interface Segregation: Each of our interfaces is small (has few functions) and has a specific purpose. For example, the `InOut` interface deals with input and output of information between the view and the interface adapters.

D - Dependency Inversion: Higher-level classes do not depend on lower-level classes in our program, and this is possible due to dependency inversion. For example, controllers depend on an interface that the view implements so that controllers (higher-level classes) do not depend on the view (a lower-level class) directly.

## Code organization

The main packaging strategies we discussed are "package by layer" and "package by feature". We decided to choose the by layer strategy because we already had a close implementation of it from Phase 0, and we felt it was the most clear for our program. Since many of our features work in tandem, we realized that grouping things by features would be difficult and suboptimal for this program. To a lesser extent we considered the "Inside/Outside" pattern because one can see creating and managing expenses as orders, but we thought that the large amount of methods we would need would turn the classes into bloaters.

## Design patterns

- We [implemented the Observer design pattern for `Budget` and `Item`](https://github.com/CSC207-UofT/course-project-group-054/pull/19) because a `Budget` object needs to be aware of changes to the attributes of the `Item`s it stores to satisfy certain constraints (for example, preventing an increase to an `Item`'s cost or quantity that would result in its `Budget`'s spending limit being exceeded). The Budget is the Observer (implementing the `VetoableChangeListener` and `PropertyChangeListener` interfaces) and the `Item`s are the Observables (having `VetoableChangeSupport` and `PropertyChangeSupport` fields that allow an `Item` to notify its Observer). When one of the attributes of an `Item` is changed, either the `firePropertyChange` or the `fireVetoableChange` method is called to notify the `Budget`, which then takes an action in the `vetoableChange` or `propertyChange` method.

- We [implemented the Strategy design pattern](https://github.com/CSC207-UofT/course-project-group-054/pull/30) to eliminate the code smell associated with the switch statement in `Budget`'s `vetoableChange` method. Both the Context and the Client are the `Budget` class; the Strategy is the `VetoableChangeResponseStrategy` interface; and the `ConcreteStrategies` are the `CostVetoableChangeResponseStrategy` and `QuantityVetoableChangeResponseStrategy` classes. `Budget`'s `vetoableChange` method still has a conditional but now creates strategy classes, and a conditional for choosing which subclass to create (like in the Simple Factory design pattern) is not a code smell. The `vetoableChange` method deals with two algorithms for responding to a vetoable change and a single `Budget` object should be able to use both algorithms, depending on which `Item` attribute is changed, so there cannot be two subclasses of `Budget` using only one algorithm each. Thus, we cannot use the Factory Method design pattern here.

## Use of GitHub features

### Issues

We have used GitHub issues, such as [Issue #17](https://github.com/CSC207-UofT/course-project-group-054/issues/17) and [Issue #20](https://github.com/CSC207-UofT/course-project-group-054/issues/20).

### Pull Requests

We have often made use of GitHub pull requests. Examples are linked to from the “Design patterns” and “Member progress and plans” sections of this document.

### Actions

We have used two GitHub actions in workflows in the `.github/workflows` folder:
- `automerge`: As we have configured it, [this action](https://github.com/marketplace/actions/merge-pull-requests-automerge-action) automatically merges pull requests that have at least one approval and that have the label `automerge` and then deletes automerged branches.
- `stale`: As we have configured it, [this action](https://github.com/marketplace/actions/close-stale-issues) labels issues and pull requests that have been open 1 day with no activity as `stale`.

## Code style and documentation

There should be no warnings when the code is opened in IntelliJ and classes and methods have Javadoc.

## Testing

Most methods in entity and use case classes are tested.

## Refactoring

We [refactored the `Budget` class](https://github.com/CSC207-UofT/course-project-group-054/pull/30) to eliminate the code smell associated with the switch statement, as described previously.

We also refactored for Clean Architecture (for example, [here](https://github.com/CSC207-UofT/course-project-group-054/pull/38) and [here](https://github.com/CSC207-UofT/course-project-group-054/pull/34)), and completed many TODOs left over from Phase 1 (for example, [here](https://github.com/CSC207-UofT/course-project-group-054/pull/40)), though there are still some TODOs yet to be done because our project is a work in progress.

## Functionality

### Persistence

We use dependency injection and annotations to link our entities and repositories together. This tells Spring what the various classes in our project are meant for. We are using a Postgresql database for storing data. We have tested this locally on our devices and also included a `.sql` file that contains code to set up the database. One the database has been set up, we can run our Spring application and find our server running on port 8080 by default. We use the `/api` path for API requests and all our static front-end (HTML, JavaScript, CSS, etc.) files will be stored in the `src/main/resources/public` folder in our project. This is a special folder that Spring uses to serve static content by default. Once our server is running, the client can visit our web app and the front-end will interact with the backend, which, in turn, will interact with our central database to fetch/create/modify the data and send a _response_ back to the front-end.

## Accessibility report

...