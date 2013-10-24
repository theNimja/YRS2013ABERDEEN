package com.github.rossrkk.stocksparser;

import java.applet.Applet;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class StockParser extends Applet
{
	public double out;
	
    public static void main(String[] args) throws InterruptedException 
    {
    	/*while (true) {
        	String[] citys = new String[]{
        			"london",
        			"frankfurt",
        			"tokyo",
        			"newyork"
        	};
        	for (int i = 0; i < citys.length; i ++) {
    	        try {
    	            new StockParser().start(/*citys[i]);
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
        	}
        	try {
        		Thread.sleep(90000);
        		} catch(InterruptedException e) {
        		} 
    	}
    	*/
    }
    
	public String city;

    @Override
    public void init(/*String city*/) //throws Exception
    {
    	getValue();
    }
    
    public double getValue() {
    	double value = this.getStock("london");
    	System.out.println(out);
    	return value;
    }
    
    public double getStock(String element) {

        URL url;
		try {
			url = new URL("https://gdata.youtube.com/feeds/api/users/3dsfun/subscriptions?v=2");
		
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName(element);

        for(int i=0; i<descNodes.getLength(); i++) {
            String content = descNodes.item(i).getTextContent();
            int length = content.length();
            int start = content.lastIndexOf(":");
            out = Double.parseDouble(content.substring(start + 2, length));
            //System.out.println(out);
            //PrintWriter fileOut = new PrintWriter("stocks" + city + ".txt");
            //fileOut.println(out);
            //fileOut.close();;
            return out;
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
    }

    public Document parseXML(InputStream stream)
    //throws Exception
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
            try {
				throw ex;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }       
        return doc;
    }
}