package com.reddit.dailyprogrammer.hughwphamill.sentiment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Application {

    private static final String GOOGLE_API = "https://plus.googleapis.com/u/0/_/widget/render/comments?first_party_property=YOUTUBE&href=";

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
        if (!input.getPath().contains("watch?v=")) {
            System.out.println("Must be a valid Youtube URL! e.g. https://www.youtube.com/watch?v=dQw4w9WgXcQ\n Usage: Application <Youtube URL>");
            System.exit(1);
        }

        try {
            URL url = new URL(GOOGLE_API + input.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Bad Response: " + connection.getResponseCode());
                System.exit(1);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
