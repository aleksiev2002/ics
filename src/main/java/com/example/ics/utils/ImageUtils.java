package com.example.ics.utils;
import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageUtils {
    public static boolean isURLValid(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isImage(String urlString) {
        try {
            URL url = new URL(urlString);
            Image image = ImageIO.read(url);
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }
}
