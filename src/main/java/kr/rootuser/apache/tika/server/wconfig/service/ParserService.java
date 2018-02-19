package kr.rootuser.apache.tika.server.wconfig.service;

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

	@GetMapping("/tika/pdf")
	public ResponseEntity<String> getTextFromPdf(@RequestParam("file") String filePath,
			@RequestParam(value = LANG, defaultValue = ENG_KOR) String language) {
		try {
			return buildResponse(parser.parse(filePath, language));
		} catch (TikaServerException e) {
			return buildResponseError(e);
		}
	}

	@GetMapping("/tika/scanpdf")
	public ResponseEntity<String> getTextFromScan(@RequestParam("file") String filePath,
			@RequestParam(value = LANG, defaultValue = ENG_KOR) String language) {
		try {
			return buildResponse(parser.parseScan(filePath, language));
		} catch (TikaServerException e) {
			return buildResponseError(e);
		}
	}
}