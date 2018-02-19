package kr.rootuser.apache.tika.server.wconfig.logic.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import kr.rootuser.apache.tika.server.wconfig.ApplicationConfig;
import kr.rootuser.apache.tika.server.wconfig.logic.PdfParser;

@Component
public class TikaPdfParser implements PdfParser {
	
	@Autowired
	private ApplicationConfig configer;

	private static final String SCAN = "scan";
	private static final String PDF = "pdf";

	public String parseScan(String filePath, String language) throws TikaServerException {
		return parse(SCAN, filePath, language);
	}

	public String parse(String filePath, String language) throws TikaServerException {
		return parse(PDF, filePath, language);
	}

	private String parse(String type, String filePath, String language) throws TikaServerException {
		BodyContentHandler handler = getContentHandler();
		try {
			getParserConfig(type).parse(buildFileStream(filePath), handler, getMetadata(), getParseContext(type, language));
		} catch (IOException | SAXException | TikaException e) {
			throw new TikaServerException("Failed to parse pdf");
		}
		return handler.toString();
	}

	private FileInputStream buildFileStream(String filePath) throws TikaServerException {
		FileInputStream stream;
		try {
			ClassLoader classLoader = TikaPdfParser.class.getClassLoader();
			File file = new File(classLoader.getResource(filePath).getFile());
			stream = new FileInputStream(file);
		} catch (FileNotFoundException | NullPointerException e) {
			throw new TikaServerException("file not found");
		}
		return stream;
	}
	
	private BodyContentHandler getContentHandler() {
		BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
		return handler;
	}

	private Metadata getMetadata() {
		Metadata metadata = new Metadata();
		return metadata;
	}

	private ParseContext getParseContext(String type, String language) {
		ParseContext parseContext = new ParseContext();
		parseContext.set(Parser.class, getParserConfig(type));
		parseContext.set(PDFParserConfig.class, getPdfConfig());
		if (type.equals(SCAN)) {
			parseContext.set(TesseractOCRConfig.class, getOcrConfig(language));
		}
		return parseContext;
	}
	
	private AutoDetectParser getParserConfig(String type) {
		if (type.equals(SCAN)) {
			TikaConfig config = TikaConfig.getDefaultConfig();
			return new AutoDetectParser(config);
		} else {
			return new AutoDetectParser();
		}
	}
	
	private PDFParserConfig getPdfConfig() {
		PDFParserConfig pdfConfig = new PDFParserConfig();
		pdfConfig.setExtractInlineImages(true);
		pdfConfig.setExtractUniqueInlineImagesOnly(false);
		return pdfConfig;
	}

	private TesseractOCRConfig getOcrConfig(String language) {
		TesseractOCRConfig ocrConfig = new TesseractOCRConfig();
		ocrConfig.setLanguage(language);
		ocrConfig.setTessdataPath(getTessdata());
		ocrConfig.setMaxFileSizeToOcr(Integer.MAX_VALUE);
		return ocrConfig;
	}

	private String getTessdata() {
		return configer.getTessdata();
	}
}