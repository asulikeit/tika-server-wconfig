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
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import kr.rootuser.apache.tika.server.wconfig.logic.PdfParser;

@Component
public class TikaPdfParser implements PdfParser {

	private static String tessdata = "/usr/share/tesseract-ocr/tessdata";

	public String parseScan(String filePath, String language) throws TikaServerException {
		return parse("scan", filePath, language);
	}

	public String parse(String filePath, String language) throws TikaServerException {
		return parse("pdf", filePath, language);
	}

	private String parse(String type, String filePath, String language) throws TikaServerException {
		FileInputStream stream;
		try {
			ClassLoader classLoader = TikaPdfParser.class.getClassLoader();
			File file = new File(classLoader.getResource(filePath).getFile());
			stream = new FileInputStream(file);
		} catch (FileNotFoundException | NullPointerException e) {
			throw new TikaServerException("file not found");
		}
		BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);

		TikaConfig config = TikaConfig.getDefaultConfig();
		Parser parser = new AutoDetectParser(config);
		Metadata metadata = new Metadata();

		TesseractOCRConfig ocrConfig = new TesseractOCRConfig();
		ocrConfig.setLanguage(language);
		ocrConfig.setTessdataPath(tessdata);
		ocrConfig.setMaxFileSizeToOcr(Integer.MAX_VALUE);

		PDFParserConfig pdfConfig = new PDFParserConfig();
		pdfConfig.setExtractInlineImages(true);
		pdfConfig.setExtractUniqueInlineImagesOnly(false);

		ParseContext parseContext = new ParseContext();
		parseContext.set(Parser.class, parser);
		parseContext.set(TesseractOCRConfig.class, ocrConfig);
		parseContext.set(PDFParserConfig.class, pdfConfig);
		
		try {
			parser.parse(stream, handler, metadata, parseContext);
		} catch (IOException | SAXException | TikaException e) {
			throw new TikaServerException("Failed to parse pdf");
		}
		
		return handler.toString();
	}
}
