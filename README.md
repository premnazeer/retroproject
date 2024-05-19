# retroproject
Springboot Project for managing Restrospective Feedback.



To Start the Application:
========================
From the project root folder (/retroproject), the following commands.

./gradlew clean build
./gradlew bootRun


This application has following endpoints:

1) To create Retrospective: 
===========================
	POST http://localhost:8081/retro/create 

Example:

curl --location 'http://localhost:8081/retro/create' \
--header 'Content-Type: application/json' \
--data '{
    "name" :                   "Retrospective 1",
    "summary":            "Post release retrospective", 
    "date":                "2022-06-28",
    "participants" :       ["Viktor", "Gareth", "Mike"]
}'


2) To Add Feedback:
==================
	POST http://localhost:8081/retro/addfeedback

Example:

curl --location 'http://localhost:8081/retro/addfeedback' \
--header 'Content-Type: application/json' \
--data '{
    "retroName" : "Retrospective 1",
    "retroDate":         "2022-06-28",      
    "name":         "Victor",
    "feedbackBody": "Sprint objective not met",
    "feedbackType": "NEGATIVE"    
}'


3) To view all retrospective details with pagination:

	GET http://localhost:8081/retro/view?pageNumber=1&pageSize=4
	
	Example:
	
	curl --location 'http://localhost:8081/retro/view'
	curl --location 'http://localhost:8081/retro/view?pageNumber=1&pageSize=4'

	
	
4) To view retrospective details by date:

	GET http://localhost:8081/retro/view/{retroDate}

Example: 

	curl --location 'http://localhost:8081/retro/view/2022-06-28'

