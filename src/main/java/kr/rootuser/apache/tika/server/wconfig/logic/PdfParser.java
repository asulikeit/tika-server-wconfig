package kr.rootuser.apache.tika.server.wconfig.logic;

import kr.rootuser.apache.tika.server.wconfig.logic.impl.TikaServerException;

public interface PdfParser {

	String parseScan(String filePath, String language) throws TikaServerException;

	String parse(String filePath, String language) throws TikaServerException;

}