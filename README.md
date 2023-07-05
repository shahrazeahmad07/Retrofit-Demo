# Retrofit-Demo
A simple retrofit demo application. I have learned how to use Retrofit library to fetch data from Rest API and update it, create new data or delete data. 

## APIs Details
I have tested this project with 2 different APIs
1st: Github API<br>
https://api.github.com/users<br>
its a very simple API, the API returns only a list of users, so the model class is very simple to make.

2nd: Reqres API<br>
https://reqres.in/api/users?page=2<br>
This API is also not very complicated but the thing to learn is that what should be the structure of the model class when the API returns not only the list but some other members too.

You can check the implementation of both APIs in branches of this repo.<br>
Main2 = Reqres API,<br>
Main = GitHub API.<br>

I have just fetched data with GitHub API becuase there were no other methods availble for that API.
But for Reqres API version I have implemented all the CRUD funcitonality (in Retrofit terms => Get, Post, Update, Delete)

## Implementation Details
So we have to simply create a Retrofit interface with methods of GET, POST, UPDATE, DELETE and then we have to create retrofit base class for making retrofit object, some people call it RetrofitHelperClass, some use other names, I called it here RetrofitUtilities.
This class gives you object of retrofit which you can then use to fetch the data or do the other operations.

## Demo Video

https://github.com/shahrazeahmad07/Retrofit-Demo/assets/68849516/23d05fdb-f28a-49a1-95e3-59525ab11dfe

