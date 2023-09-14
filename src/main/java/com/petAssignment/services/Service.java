package com.petAssignment.services;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class Service {

	public boolean findId(long id) {
		System.out.print(id);
		try {
			String uri = "https://petstore.swagger.io/v2/pet/"+id;
			RestTemplate rest = new RestTemplate();
			String result = rest.getForObject(uri,String.class);
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}	
}
