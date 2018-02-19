package kr.rootuser.apache.tika.server.wconfig.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.rootuser.apache.tika.server.wconfig.ParserConstants;
import kr.rootuser.apache.tika.server.wconfig.logic.impl.TikaServerException;

public class AbstractService {

	protected static final String ENG_KOR = "eng+kor";
	protected static final String LANG = "lang";

	protected ResponseEntity<String> buildResponse(String content) {
		return new ResponseEntity<String>(content, HttpStatus.OK);
	}

	protected ResponseEntity<String> buildResponseError(TikaServerException e) {
		if (e == null || e.getMessage() == null || !e.getMessage().equals(ParserConstants.FILE_NOTFOUND)) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
