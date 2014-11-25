package com.reddit.dailyprogrammer.hughwphamill.sentiment;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentExtractor {
    public List<String> extract(URL url) throws IOException, SAXException, ParserConfigurationException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("Bad Response: " + connection.getResponseCode());
            System.exit(1);
        }

        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setXHTML(true);
        tidy.setShowErrors(0);
        tidy.setShowWarnings(false);
        Document dom = tidy.parseDOM(connection.getInputStream(), null);
        NodeList divs = dom.getElementsByTagName("div");

        List<String> comments = new ArrayList<>();

        for (int i = 0; i < divs.getLength(); i++) {
            Node div = divs.item(i);

            NamedNodeMap attributes = div.getAttributes();
            Node clss = attributes.getNamedItem("class");
            if (clss != null) {
                if (clss.getNodeValue().contains("Ct")) {
                    NodeList children = div.getChildNodes();
                    for (int j = 0; j < children.getLength(); j++) {
                        Node child = children.item(j);
                        if (child.getNodeName().equalsIgnoreCase("#text")) {
                            comments.add(child.getNodeValue());
                        }
                    }
                }
            }
        }

        return comments;
    }
}
