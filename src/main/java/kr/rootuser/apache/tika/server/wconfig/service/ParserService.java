package kr.rootuser.apache.tika.server.wconfig.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.rootuser.apache.tika.server.wconfig.logic.PdfParser;
import kr.rootuser.apache.tika.server.wconfig.logic.impl.TikaServerException;

@RestController
public class ParserService extends AbstractService {

	@Autowired
	PdfParser parser;

	@GetMapping("/tika/pdf/**")
	public ResponseEntity<String> getTextFromPdf(HttpServletRequest request,
			@RequestParam(value = LANG, defaultValue = ENG_KOR) String language) {
		try {
			return buildResponse(parser.parse(getFilepath(request), language));
		} catch (TikaServerException e) {
			return buildResponseError(e);
		}
	}

	@GetMapping("/tika/scanpdf/**")
	public ResponseEntity<String> getTextFromScan(HttpServletRequest request,
			@RequestParam(value = LANG, defaultValue = ENG_KOR) String language) {
		try {
			return buildResponse(parser.parseScan(getFilepath(request), language));
		} catch (TikaServerException e) {
			return buildResponseError(e);
		}
	}
}