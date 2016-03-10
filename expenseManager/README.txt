Designer/Developer: Jason Cunningham
Date: 3/06/2016


Included with this zip file should be the following directory structure and files. All RESTFul endpoints are accessed from RestapiApplication and will return an HTTP 200 ok message. Any errors are sent back to the client in the form of a string message for display. If you wish to query your mongo db from the command line, the database for this application is named below. 

src/main/java/expense/api
	- RestapiApplication (API end points)

src/main/java/expense/das
	- DAO (Interface)
	- MongoDAOImpl (Implements DAO)

src/main/webapp
	- merchantExpense.html (Expense entry form web page)

Mongo DB Name - expense_db

To build - ./gradlew build
To Run - ./gradlew bootRun
URL - localhost:8080 (or whatever host:port you have the application running on.

Navigate to the above URL and the application will route you to the merchantExpense.html page. Happy expense entering…

Assumptions:
I have removed the Spring annotations that were defaulted in the top of the RestapiApplication class and replaced with @EnabledAutoConfiguration and @RestController. These new annotations tell the Spring container that there should not be a default authentication required to exercise an API call, as well as, explicitly let the container know that the class is used as an MVC controller.

All form field validation is handled on the client side using javascript, and all form submissions are handled via angularJS and the $http browser object. 

Functions on the web page:
Find - will search the DB to find a record having the ID entered on the form.
Find All - will return all the records in the DB in order of latest first. No ID form entry is required.
Reset - clears the form.
Delete - removes a record if applicable.
Save - will save or update a record. If a record has been search upon and return from the DB, then a save action will update the record. Otherwise a new record is created and it’s ID is returned the client.