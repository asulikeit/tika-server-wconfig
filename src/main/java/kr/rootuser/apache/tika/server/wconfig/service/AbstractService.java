package kr.rootuser.apache.tika.server.wconfig.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.rootuser.apache.tika.server.wconfig.logic.impl.TikaServerException;

public class AbstractService {
	
	protected ResponseEntity<String> buildResponse(String content) {
		return new ResponseEntity<String>(content, HttpStatus.OK);
	}
	
	protected ResponseEntity<String> buildResponseError(TikaServerException e) {
		if(e == null || e.getMessage() == null || !e.getMessage().equals("file not found")) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);	
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
