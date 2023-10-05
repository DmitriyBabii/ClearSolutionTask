# ClearSolution task
Test task for Clear Solutions

## API
> If the operation is successful, a text plain message will be displayed, else a JSON exception object will be returned.

> GET method returns JSON only.

> There are different statuses for response such as: ok, bad request, update required, no content.

### Get users
```
GET localhost/users 
```

### Get users between two dates
> There is check for fromDate is earlier than toDate; if it is not so, then the dates will be swapped
```
GET localhost/users?from=2000-01-01&to=2002-01-01
```

### Create new user
```
POST localhost/users

body:
{
    "firstName": "Dima",
    "lastName": "Babii",
    "email": "email@g",
    "birthDate": "2002-10-17",
    "address": "Somewhere",
    "phoneNumberr": "+380000000000"
}
```

### Full user update
```
PUT localhost/users/{userId}

body:
{
    "firstName": "Dmytro",
    "lastName": "Babiy",
    "email": "updatedEmail@g",
    "birthDate": "2002-11-17",
    "address": "Somewhere updated",
    "phoneNumberr": "+380000000001"
}
```

### Contact user update
```
PATCH localhost/users/{userId}

body:
{
    "email": "updatedEmail@g",
    "address": "Somewhere updated",
    "phoneNumberr": "+380000000001"
}
```

### Delete user
```
DELETE localhost/users/{userId}
```

## Data (DataBase)
**MySQL** database is used to store information

## Validation
Validation was done using annotations in the User class and using **DateUtils** class for date.

> Phone number must be in the format +380XXXXXXXXX

> Email must be valid (with '@')

> All String data must be NotBlank (but phone number can be null)

## Properties
User must be 18 years old so there is '**user.min-age**' in the properties.
> This property is used in the **UserService**.

## ExceptionHandler
There is **ApiExceptionHandler** that has methods to process exceptions that may occur during work.
> **ApiExceptionHandler** process such Exceptions as: **ApiRequestException**(custom), **ConstraintViolationException**, **SQLIntegrityConstraintViolationException**

> You will get message by any exception such as incorrect data.


## Tests
There are tests for **UserRepository**, **UserService**, **UserController**.
> A total of 37 tests were developed.