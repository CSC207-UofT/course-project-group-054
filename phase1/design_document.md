# Design Document

## Your updated specification.
A program that enables users to create groups, split expenses with one another in groups, and set budgets. There are two kinds of users: users that have an account, and users that do not have an account. Every user has a balance, a name, an email address, and a mapping from other users to the amounts of money owed by this user to the other users. Only users that have an account have a unique identifier and a profile that they can edit and can create a group. Users without an account are placeholder user objects that users with accounts can create in order to track expenses incurred by people who do not have an account.

**Groups organize users so that users can share expenses with others in the group. Each expense is associated with a cost, date, a mapping between users and the amounts of money owed by those users, and notes. Each group contains a list of users and a list of expenses associated with the group. Each group is made up of at least one user with an account. Each expense contains hashmaps of people to amounts that were borrowed _or_ amounts that were lent. Through this Users can pay off the money they borrowed using their balance.** A user with an account can add another user with an account to a group. Any user in a group that has an account can remove itself or any other user from the group and add an expense to the group. Users with accounts can add users without accounts to the group as placeholders representing people without an account to account for amounts of money owed by those people. Any user in a group that does not have an account cannot add expenses to or remove any user, including itself, from a group.

**Groups also have budgets. A budgets stores a list of items, each of which has a name, cost and quantity. A budget has a spending limit: the maximum amount of money that can be spent on items in the budget. The items in a budget can be converted into expenses that can be added to the group that contains the budget.** 

**Changes**
In phase zero, the ability to store who borrowed/lent what amount was not saved, however now it is. 

In phase zero, our app relied on solely on the command line. Since then, we have made progress regarding the front end of our app using Spring, HTML, CSS, and Javascript.

## OPTIONAL: Diagram of Code

## A description of any major design decisions your group has made (along with brief explanations of why you made them).
In phase zero, the items in a budget were grouped into categories, but this is no longer the case in phase one.
because the functionality provided by categories was also served by the item name.
The purpose of categories was to organize items into subgroups within a budget. Since a group can have multiple budgets, 

We have decided to keep the command line interface _and_ the web interface, both for ease of rough testing ease of use for the target user.

We decided to use Spring for our web application, and have created new files and refactored our files and folders to adhere to the Web app design requirements.

## A brief description of how your project adheres to Clean Architecture (if you notice a violation and aren't sure how to fix it, talk about that too!)



## A brief description of how your project is consistent with the SOLID design principles (if you notice a violation and aren't sure how to fix it, talk about that too!)

## A brief description of which packaging strategies you considered, which you decided to use, and why. (see slide 7 from the packages slides)

## A summary of any design patterns your group has implemented (or plans to implement).

## A progress report (open questions your group is struggling with, what has worked well so far with your design, a summary of what each group member has been working on and plans to work on next