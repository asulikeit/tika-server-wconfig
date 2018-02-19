package kr.rootuser.apache.tika.server.wconfig.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

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

	protected String getFilepath(HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		AntPathMatcher apm = new AntPathMatcher();
		String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);
		return finalPath;
	}
}
