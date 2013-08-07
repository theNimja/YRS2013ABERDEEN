package com.github.rossrkk.stocksparser;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class StockParser
{
    public static void main(String[] args) throws InterruptedException 
    {
    	while (true) {
        	String[] citys = new String[]{
        			"london",
        			"frankfurt",
        			"tokyo",
        			"newyork"
        	};
        	for (int i = 0; i < citys.length; i ++) {
    	        try {
    	            new StockParser().start(citys[i]);
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
        	}
        	try {
        		Thread.sleep(90000);
        		} catch(InterruptedException e) {
        		} 
    	}
    }

    private void start(String city) throws Exception
    {
    	
    	
        URL url = new URL("https://spreadsheets.google.com/feeds/list/0AgoGEsvdq-VLdFpDNlk0SHNEYlRiekJuVVdCOXFmRFE/od6/public/basic?sq=city=" + city);
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("content");

        for(int i=0; i<descNodes.getLength();i++) {
            String content = descNodes.item(i).getTextContent();
            int length = content.length();
            int start = content.lastIndexOf(":");
            double out = Double.parseDouble(content.substring(start + 2, length));
            System.out.println(out);
            PrintWriter fileOut = new PrintWriter("stocks" + city + ".txt");
            fileOut.println(out);
            fileOut.close();
        }
    }

    private Document parseXML(InputStream stream)
    throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            throw ex;
        }       
        return doc;
    }
}