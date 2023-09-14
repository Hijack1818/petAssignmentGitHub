package com.petAssignment.services;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class Service {

	//find already exist id
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

	//extract id from the body
	public long extractID(String body) {
		int first = body.indexOf("id");
		first = body.indexOf(":",first);
		int last = body.indexOf(",", first);
		String strId = body.substring(first+2,last);
		return Integer.parseInt(strId);
	}	
	
	
}
