package com.petAssignment.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petAssignment.entities.Pet;
import com.petAssignment.services.Service;

@RestController
public class Controller {
	
	@Autowired
	public Service service;
	
	@PostMapping("/savePet")
	String savePet(@RequestBody String body) {
		
		long id = service.extractID(body);
		
		String uri = "https://petstore.swagger.io/v2/pet";
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
        	
    		if(service.findId(id)) {
    			return "{code:409, message:Data with this id already exist}";
    		}
        	
	        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
	        
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);
			
	        String responseBody = responseEntity.getBody();
			return "{code:200, message:"+responseBody+"}";
        }
        catch(Exception ex) {
        	return "{code:400, message:input valid field}";
        }
		
	}
	
	@GetMapping("/getPet/{id}")
	public ResponseEntity<String> getPet(@PathVariable long id) {
	    try {
	        String uri = "https://petstore.swagger.io/v2/pet/" + id;
	        RestTemplate rest = new RestTemplate();
	        String result = rest.getForObject(uri, String.class);
	        return ResponseEntity.ok(result);
	    } catch (HttpClientErrorException ex) {
	        int statusCode = ex.getRawStatusCode();
	        if (statusCode == 404) {
	            return ResponseEntity.badRequest().body("{code : 404, message : 'Pet Not Found'}");
	        } else if (statusCode == 400) {
	            return ResponseEntity.badRequest().body("{code : 400, message : 'Enter Valid Pet Id'}");
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
	    }
	}

	
	@PatchMapping("/updatePet")
	public ResponseEntity<String> updatePet(@RequestBody String body) {
		Pet pet,finalPet;
		long id;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
		    pet = objectMapper.readValue(body, Pet.class);
		    
		    id = pet.getId();
			ResponseEntity<String> updatePet = getPet(id);
			
			System.out.println(updatePet.getBody());
			
			if (updatePet.getStatusCode() == HttpStatus.BAD_REQUEST) {
		        return ResponseEntity.badRequest().body("{code:404, message:'Data with this ID does not exist'}");
		    }

		    if (updatePet.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{code:500, message:'Error while fetching pet data from the external API'}");
		    }
			
		    finalPet =  objectMapper.readValue(updatePet.getBody(), Pet.class);
		    if(pet.getName()!="") {
		    	finalPet.setName(pet.getName());
		    }
		    if(pet.getStatus()!="") {
		    	finalPet.setStatus(pet.getStatus());
		    }
		    
		} catch (IOException ex) {
		    ex.printStackTrace();
		    return ResponseEntity.badRequest().body("{code:400, message:'Invalid JSON format'}");
		}
		
		String uri = "https://petstore.swagger.io/v2/pet/"+id;
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
        	
//        	String updatedBody = objectMapper.writeValueAsString(finalPet);
//        	System.out.println(updatedBody);
//        	System.out.println(finalPet);
//        	System.out.println(body);
	        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
	        
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);
			
	        String responseBody = responseEntity.getBody();
			return ResponseEntity.ok(responseBody);
        }
        catch(Exception ex) {
        	return ResponseEntity.badRequest().body(ex.toString());
        }
		
	}
	
	
	@DeleteMapping("/deletePet/{id}")
	public ResponseEntity<String> deletePet(@PathVariable long id) {
	    try {
	        String uri = "https://petstore.swagger.io/v2/pet/" + id;

	        // Check if the data with the given ID exists before making the delete request
	        ResponseEntity<String> checkResponse = getPet(id);
	        if (checkResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
	        	return new ResponseEntity<>("{code: 404, message: 'Data with this ID does not exist'}", HttpStatus.NOT_FOUND);
	        }
	        
	        RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, Void.class);
            
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Pet deleted successfully");
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body("Failed to delete pet");
            }
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.badRequest().body("Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + ex.getMessage());
        }
            
	}

	
	
}
