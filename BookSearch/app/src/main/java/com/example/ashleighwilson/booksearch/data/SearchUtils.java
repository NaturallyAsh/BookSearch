package com.example.ashleighwilson.booksearch.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.ashleighwilson.booksearch.models.Book;
import com.example.ashleighwilson.booksearch.models.GoogleImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SearchUtils
{
    private static final String TAG = SearchUtils.class.getSimpleName();

    public SearchUtils()
    {
    }

    private static URL createURL(String stringUrl)
    {
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }

        return url;
    }

    public static List<Book> fetchBookData(String requestUrl)
    {
        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream", e);
        }

        List<Book> books = extractBookInfoFromJson(jsonResponse);

        return books;
    }

    public static List<GoogleImage> fetchImageData(String requestUrl)
    {
        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream", e);
        }

        List<GoogleImage> googleImages = extractImageFromJson(jsonResponse);

        return googleImages;
    }

    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        if (url == null)
        {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    //Extract Bitmap from given URL string
    private static Bitmap getThumbnail(String imageURL)
    {
        URL url = createURL(imageURL);
        Bitmap thumbnail = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200)
            {
                InputStream stream = connection.getInputStream();
                thumbnail = BitmapFactory.decodeStream(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return thumbnail;
    }

    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<GoogleImage> extractImageFromJson(String imageJSON) {
        List<GoogleImage> googleImages = new ArrayList<>();
        try {
            JSONObject imageJsonResponse = new JSONObject(imageJSON);
            JSONArray imageArray = imageJsonResponse.getJSONArray("items");
            for (int i = 0; i < imageArray.length(); i++) {
                JSONObject currentBook = imageArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                JSONObject imagelinks = volumeInfo.getJSONObject("imageLinks");
                String mthumbnail = imagelinks.optString("smallThumbnail");
                String imageUrl = "";
                Bitmap thumbnail = null;


                if (imagelinks != null)
                    imageUrl = imagelinks.optString("smallThumbnail");
                if (!imageUrl.isEmpty())
                    thumbnail = getThumbnail(imageUrl);

                googleImages.add(new GoogleImage(thumbnail));
            }
        } catch (JSONException e) {
            Log.e("SearchUtils", "Problem parsing the book JSON results", e);
        }
        return googleImages;
    }

    private static List<Book> extractBookInfoFromJson(String booksJSON)
    {
        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(booksJSON);

            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++)
            {
                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String bookTitle = volumeInfo.optString("title");
                String infoLink = volumeInfo.optString("infoLink");
                String bookDescription = volumeInfo.optString("description");
                if (bookDescription.length() > 150)
                {
                    bookDescription = bookDescription.substring(0, 150) + "...";
                }
                String authors;
                if (volumeInfo.has("authors"))
                {
                    JSONArray authorsJsonArray = volumeInfo.getJSONArray("authors");
                    authors = authorsJsonArray.optString(0);
                    for (int j = 0; j < authorsJsonArray.length(); j++)
                    {
                        if (authorsJsonArray.length() == 1)
                        {
                            authors = authorsJsonArray.optString(j) + " ";
                        }
                        else if (authorsJsonArray.length() > 1)
                        {
                            authors = authors + ", " + authorsJsonArray.optString(j);
                        }
                    }
                }
                else
                {
                    authors = "No authors listed";
                }
                JSONObject imagelinks = volumeInfo.getJSONObject("imageLinks");
                String mthumbnail = imagelinks.optString("smallThumbnail");
                String imageUrl = "";
                Bitmap thumbnail = null;
                if (imagelinks != null)
                    imageUrl = imagelinks.optString("smallThumbnail");
                if (!imageUrl.isEmpty())
                    thumbnail = getThumbnail(imageUrl);

                books.add(new Book(thumbnail, bookTitle, authors, bookDescription, infoLink));
            }
        } catch (JSONException e) {
            Log.e("SearchUtils", "Problem parsing the book JSON results", e);
        }

        return books;
    }
}
