package com.club.web.util;

import java.io.*;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
/**
 * html格式转string工具类
 * @author zhuzd
 *
 */
public class Html2Text extends HTMLEditorKit.ParserCallback {
	
	private static Html2Text instance = new Html2Text();
	
	public static Html2Text getInstance(){
		return instance;
	}
	
	StringBuffer s;

	public Html2Text() {
	}

	public void parse(Reader in) throws IOException {
		s = new StringBuffer();
		ParserDelegator delegator = new ParserDelegator();
		// the third parameter is TRUE to ignore charset directive
		delegator.parse(in, this, Boolean.TRUE);
	}

	public void handleText(char[] text, int pos) {
		s.append(text);
	}

	public String getText() {
		return s.toString();
	}

	public String convert(String html) {
		StringReader in = new StringReader(html);
		Html2Text parser = new Html2Text();
		try {
			parser.parse(in);
		} catch (IOException e) {
			return "";
		}

		in.close();
		return parser.getText();
	}
	
	public String transfromSensitive(String content) {
		if (content == null) {
			return null;
		}
		String str = new String();
		str = content.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;
	}
}
