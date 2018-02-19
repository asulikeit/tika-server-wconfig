package kr.rootuser.apache.tika.server.wconfig.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.rootuser.apache.tika.server.wconfig.logic.PdfParser;
import kr.rootuser.apache.tika.server.wconfig.logic.impl.TikaServerException;

@RestController
public class ParserService extends AbstractService {

	@Autowired
	PdfParser parser;
	
	private static final Logger LOG = LoggerFactory.getLogger(ParserService.class);

	@GetMapping("/tika/pdf/{file:.+}")
	public ResponseEntity<String> getTextFromPdf(@PathVariable("file") String filePath,
			@RequestParam(value = "lang", defaultValue = "eng+kor") String language) {
		try {
			LOG.debug("Try to parse file - {} ", filePath);
			return buildResponse(parser.parse(filePath, language));
		} catch (TikaServerException e) {
			return buildResponseError(e);
		}
	}

	@GetMapping("/tika/scanpdf/{file:.+}")
	public ResponseEntity<String> getTextFromScan(@PathVariable("file") String filePath,
			@RequestParam(value = "lang", defaultValue = "eng+kor") String language) {
		try {
			LOG.debug("Try to parse file - {} ", filePath);
			return buildResponse(parser.parseScan(filePath, language));
		} catch (TikaServerException e) {
			return buildResponseError(e);
		}
	}

}