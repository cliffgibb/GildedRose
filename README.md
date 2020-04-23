# The Gilded Rose Public API

This sample application was created by Cliff Fenwick Gibb for Mobile Integration WorkGroup. 

### Installation
```
mvn clean package
```
### How to Run
```
java -jar GildedRose-0.0.1-SNAPSHOT.jar
```
### How to Test
```
mvn clean test
```
### About the Service
This application has 2 public HTTP-accessible endpoints;
 - View the item inventory (paginated)
 - Purchase an item
 
 The price of an item will increase by 10% if the item has been viewed more than 10 times within the past hour.

###REST API
####Inventory
#####Request
Query the first page of results from the item inventory.
```
GET /inventory/
```
#####Response
```
HTTP/1.1 200 OK
Date: Wed, 22 Apr 2020 12:36:30 GMT
Status: 200 OK
Connection: close
Content-Type: application/json
{
	"content": [
		{
			"id": 1,
			"name": "Scented Candle",
			"description": "A lavender scented candle",
			"price": 9.0
		},
		{
			"id": 2,
			"name": "Hand Soap",
			"description": "Organic oatmeal hand soap",
			"price": 4.0
		},
		{
			"id": 3,
			"name": "Sleep mask",
			"description": "A sleep mask with a gel pack for heating or cooling",
			"price": 30.0
		},
		...
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"unsorted": true,
			"empty": true
		},
		"pageSize": 10,
		"pageNumber": 0,
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"totalPages": 4,
	"last": false,
	"totalElements": 39,
	"numberOfElements": 10,
	"first": true,
	"size": 10,
	"number": 0,
	"sort": {
		"sorted": false,
		"unsorted": true,
		"empty": true
	},
	"empty": false
}
```
#####Request
Query the specified page of results from the item inventory.
```
GET /inventory/{pageNumber}
```
#####Response
```
HTTP/1.1 200 OK
Date: Wed, 22 Apr 2020 12:36:30 GMT
Status: 200 OK
Connection: close
Content-Type: application/json
{
	"content": [
		{
			"id": 11,
			"name": "Chocolate",
			"description": "Belgium chocolate",
			"price": 7.0
		},
		{
			"id": 12,
			"name": "Luggage",
			"description": "Lightweight luggage",
			"price": 40.0
		},
		...
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"unsorted": true,
			"empty": true
		},
		"pageSize": 10,
		"pageNumber": 1,
		"offset": 10,
		"paged": true,
		"unpaged": false
	},
	"totalPages": 4,
	"last": false,
	"totalElements": 39,
	"numberOfElements": 10,
	"first": false,
	"size": 10,
	"number": 1,
	"sort": {
		"sorted": false,
		"unsorted": true,
		"empty": true
	},
	"empty": false
}
```
#####Request
Query a page that is out of range.
```
GET /inventory/{pageNumber out of range}
```
#####Response
```
HTTP/1.1 404    
Date: Wed, 22 Apr 2020 12:36:30 GMT
Status: 404 NOT_FOUND
Connection: close
Content-Type: application/json
{
	"statusCode": "NOT_FOUND_ERROR",
	"message": "Invalid page selection. There are no items for this page.",
	"status": 404
}
```
####Inventory
#####Request
Purchasing an Item requires basic authentication and can be tested using the following credentials: 
 - Username: testuser
- Password: securepassword
#####Request
Purchase an item.
```
GET /purchase/{itemId}
```
#####Response
```
HTTP/1.1 200    
Date: Wed, 22 Apr 2020 12:36:30 GMT
Status: 200 OK
Connection: close
Content-Type: application/json
{
    "statusCode": "OK",
    "message": "Purchase complete.",
    "status": 200
}
```

#####Request
Attempt to purchase an item that doesn't exist
```
GET /purchase/{invalid itemId}
```
#####Response
```
HTTP/1.1 400    
Date: Wed, 22 Apr 2020 12:36:30 GMT
Status: 400 BAD_REQUEST
Connection: close
Content-Type: application/json
{
    "statusCode": "BAD_REQUEST",
    "message": "There are no items found matching the passed ID.",
    "status": 400
}
```
#####Request
Attempt to purchase an item that is out of stock
```
GET /purchase/{itemId}
```
#####Response
```
HTTP/1.1 400    
Date: Wed, 22 Apr 2020 12:36:30 GMT
Status: 400 BAD_REQUEST
Connection: close
Content-Type: application/json
{
    "statusCode": "BAD_REQUEST",
    "message": "This item is no longer in stock.",
    "status": 400
}
```
## Application Description
### Setup
I began using spring initializr to create a Sporing application that included Sporing MVC, Spring JPA, Spring Security and Lombok.
### Design
I created the package structure to contain all of the logical grouping of classes:
-   config
    - The DB initialization
    - Security configuration
- controller
    - The API Controller with the endpoint definitions
- custoresponse
    - The DTO for the custom JSON messages for
        - errors
        - purchase acknowledgment
    - A factory class for creating the purchase acknowledgment message
 - entity
    - the JPA entities
- exception
    - the custom exception classes
    - the handler that captures controller exceptions and translates them into custom messages to be returned to the user
- repository
    -  the repository classes that interact directly with the database
- security
    - supporting class for finding authorized users
- service
    - the services used by the controller
- application.properies
    - this file contains the values used by the application such as:
        - items.per.page
        - surge.pricing.threshold
        - surge.price.factor
### Surge pricing 
In order to fulfill the surge pricing requirement, every time an item is viewed (from the /inventory endpoint), the application stores a record that includes the item, and the time.
When the system generates the page a results from the /inventory endpoint, it performs a query to count how many views there have been for each item in the last hour. If that number exceeds the defined threshhold, then the price is increased by 10%.

### Data Format
The data format is JSON.
### Authentication Mechanism 
Basic Authentication was chosen as the authentication mechanism so that the authentication could be verified with each request.
The requirements stated that there were to be 2 HTTP endpoints. A token-based authentication mechanism would have required an additional endpoint.

### Future Improvements
#### Inventory Paginated Results
- The paginated results currently contains more data than what is needed. This could be mapped to a DTO to streamline the returned object.
- An optional parameter of 'Items per page' could be passed to give the end user the ability to return more results per page.
- It may be desirable to include the amount of stock for each item in the results.
###Purchase Endpoint 
- This endpoint should be change to be a POST mapping.
- The payload could include quantity to allow the purchase of multiple items as a time.
###Scheduled ViewLog Clean-up
The ViewLog Entities should be cleaned-up on a scheduled basis.
