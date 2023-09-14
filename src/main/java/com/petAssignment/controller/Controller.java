package com.petAssignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.petAssignment.services.Service;

@RestController
public class Controller {
	
	@Autowired
	public Service service;
	
	@RequestMapping("new")
	String home() {
		return "";
	}
	
	@PostMapping("/savePet")
	String savePet(@RequestBody String body) {
		
//		System.out.print(body);
		
		int first = body.indexOf("id");
		first = body.indexOf(":",first);
		int last = body.indexOf(",", first);
		String strId = body.substring(first+2,last);
//		System.out.print(body.substring(first+1,last));
		
		String uri = "https://petstore.swagger.io/v2/pet";
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
        	
        	long id = Integer.parseInt(strId);
//    		System.out.print(id);
    		
    		if(service.findId(id)) {
    			return "{code:409, message:Data with this id already exist}";
    		}
        	
	        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
	        
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);
			
	        String responseBody = responseEntity.getBody();
//	        int statusCode = responseEntity.getStatusCodeValue();
//	        System.out.println("Status Code: " + statusCode);
//	        System.out.println("Response Body: " + responseBody);
	        
			return "{code:200, message:"+responseBody+"}";
        }
        catch(Exception ex) {
        	return "{code:400, message:input valid field}";
        }
		
	}
	
	
	//getting pet using pet id
	@GetMapping("/getPet/{id}")
	String getPet(@PathVariable int id) {
		try {
			String uri = "https://petstore.swagger.io/v2/pet/"+id;
			RestTemplate rest = new RestTemplate();
			String result = rest.getForObject(uri,String.class);
			return result;
		}
		catch(HttpClientErrorException ex) {
			int statusCode = ex.getRawStatusCode();
			if(statusCode==404) {
				return "{code : 404, message : Pet Not Found";
			}
			else if(statusCode==400) {
				return "{code : 400,message : Enter Valid Pet Id";
			}
			return ex.getMessage();
		}
		catch(Exception ex) {
			return ex.toString();
		}
	}
	
}
