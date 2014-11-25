package com.reddit.dailyprogrammer.hughwphamill.sentiment;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Application {

    private static final String GOOGLE_API = "https://plus.googleapis.com/u/0/_/widget/render/comments?first_party_property=YOUTUBE&href=";
    private static final String[] HAPPY = {"love","loved","like","liked","awesome","amazing","good","great","excellent"};
    private static final String[] SAD = {"hate","hated","dislike","disliked","awful","terrible","bad","painful","worst"};

    public static void main(String... args) {
        if (args.length < 1) {
            System.out.println("Usage: Application <Youtube URL>");
            System.exit(1);
        }

        URL input = null;
        try {
            input = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.out.println("Bad URL!\n Usage: Application <Youtube URL>");
            System.exit(1);
        }
        if (!input.getHost().equalsIgnoreCase("www.youtube.com")) {
            System.out.println("Must be a Youtube URL! [" + input.getHost() + "]\n Usage: Application <Youtube URL>");
            System.exit(1);
        }
        if (!input.toString().contains("watch?v=")) {
            System.out.println("Must be a valid Youtube URL! e.g. https://www.youtube.com/watch?v=dQw4w9WgXcQ\n Usage: Application <Youtube URL>");
            System.exit(1);
        }

        try {
            URL url = new URL(GOOGLE_API + input.toString());

            System.out.println("Extracting Comments...");
            List<String> comments = new CommentExtractor().extract(url);

            System.out.println("Calculating Sentiment...");
            int score = new SentimentAnalyzer(HAPPY, SAD).analyze(comments);

            System.out.println("Score: " + score + " - mostly "  + (score >= 0 ? "happy" : "sad") + ", based on " + comments.size() + " reviews.");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
