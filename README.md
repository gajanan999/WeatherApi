# WeatherApi

##  How to run the Project
	Pre-requisite : To run the project, you need to have a following softwares/Tools installed into machine
	1. Java 8
	2. Maven
	
	Set the JAVA_HOME & MAVEN_HOME enviroment variable, After that continue with following steps
	1. open a CMD
	2. Go the project directory
	3. run the following command
	    mvn install
	    mvn package
	4. In the project directory you will be able to see the target folder has been created
	5. Go to the target folder through the command line and run the following command
	   java -jar ${jar-name.jar}
	    
##  Rest API Details
	This API Application support the following operations
	1. Login (Generate the authentication JWT token)
	   Login - Http Method - POST - /login
	   
	2. User - Create, Update, delete, Get User By user id, Get All User
		Create - Http Method - POST - /signup
		Update - Http Method - PUT - /weatherapi/users/{user_id}
		Delete - Http Method - DELETE - /weatherapi/users/{user_id}
		Get User By Id - Http Method - Get -/weatherapi/users/{user_id}
		Get All User - Http Method - Get -  /weatherapi/users/
		
	3. Get the Weather Details By City name
	   Get Weather Details by City Name - Http Method - Get - /weatherapi/search/{cityname}
	   
	4. Search the User Search history, Update the User search history
	   Search User Search History - Http Method - GET - /weatherapi/user-search-history?page=0&size=5
	   Update the User Search History- Http Method - PUT -/weatherapi/user-search-history
	   Delete the User Search history Http Method - POST -/weatherapi/user-search-history/delete
	
##  Swagger-UI Details
	You can also have swagger-ui to see and try to execute the Rest API through web browser, You can also use post man
	the project also contains the postman collection file WeatherAPI.postman_collection.json
	you just have to import it into the postman
	
	For swagger-ui use the following url 
		 http://{hostname}:8080/swagger-ui.html
		 
		 
##  Log file 
	log file will be created at same directory where you are running the project
	you can configure logger details using application.property file
	
##  Future Scope
