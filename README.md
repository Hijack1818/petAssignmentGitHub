Pet Management API
This Spring Boot application provides a simple Pet Management API with endpoints for creating, updating, retrieving, and deleting pet records. It interacts with an external Pet Store API for data operations.

Endpoints
Create a New Pet
POST /savePet

Creates a new pet record by sending a JSON request body containing the pet's details. The expected JSON request body format:

json
Copy code
{
  "id": 1,
  "category": {
    "id": 0,
    "name": "string"
  },
  "name": "doggie",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 0,
      "name": "string"
    }
  ],
  "status": "available"
}
Update an Existing Pet
PATCH /updatePet

Updates an existing pet record by sending a JSON request body with the changes to be applied. The expected JSON request body format:

json
Copy code
{
  "id": 1,
  "name": "amit",
  "status": "available"
}
Retrieve Pet Data
GET /getPet/{id}

Retrieves pet data by specifying the pet's ID in the URL path.

Delete a Pet
DELETE /deletePet/{id}

Deletes a pet record by specifying the pet's ID in the URL path.

Error Handling
The API handles various error scenarios, including:

Data with the specified ID not found (returns a 404 response).
Invalid JSON format in the request (returns a 400 response).
Internal server errors (returns a 500 response).
Getting Started
Clone this repository.
Configure the external Pet Store API URL in your application properties.
Build and run the Spring Boot application.
Use your preferred API client (e.g., Postman) to interact with the endpoints.
