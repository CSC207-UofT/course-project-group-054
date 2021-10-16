# Changelog
> Note: many tests may fail as a result of changes to variable names over the three major changes below.
> The code may also be different from our CRC models.

## Features implemented so far
- [x] Add an expense (Group expense still pending)
- [x] Show groups
- [x] Check balance
- [ ] Update profile
- [x] Create a new group
- [x] Log out

## Many small changes
- Added `View` package to manage all the views. Now we don't need to have every view implemented in the controller.
- Made many small minor changes to the code.
- Implemented *Check balance* feature.
- Partially completed `createGroupExpense` method in `Expense` class
- Overridden `toString()` method in `Group` class


## Feature to create groups
### Changes
- commented ExtendedGroup & TemporaryGroup (**for now**, will fix this)
- created a `getUUID` method in `Controller` class to get user UID using email (not tested the method)
- created `createGroupView` method that uses a CLI to allow users to create a group
- changed some return types and variable types in `Group` class (we can use original types after program is fully completed)

### Feature desctiption
Users can now create groups with any name and a dummy description.

---
## Feature to create new expenses
### Made a lot of changes:
- changed a lot of variable types and names. Also made changes to some method return types and names
- most tests would not work but only require slight modification for them to work properly
- Changed User.uid to User.UUID (Reason: added attributes for unique ids to `Expense` EUID and `Group` GUID)
- Changed User.getUID name to User.getUUID and return type to String
- Changed many method names and return types in Expense class
- Added `Controller.createExpenseView()` to separate it from main method
- 1 Add an expense is now managed by createExpenseView() instead of GroupManager class
- Added functionality to add group expenses through createControllerView
- Added Data class that emulates a database and contains all information about all registered users.
- Added dummy data (users and expenses) to Data class
- Added `isGroupExpense` attribute to expense class
- Changed variable names and types for many attributes in Expense class

### Good News
- Users are now able to create individual expenses and a lot of code is now cleaned up.
- It will now be very easy to expand the code to allow users to create group expenses.