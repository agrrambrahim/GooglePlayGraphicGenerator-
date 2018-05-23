package sample;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

    public class FxCustomGoogleSearch {

        final static String apiKey = "AIzaSyBj-ITJkHzmIvWwx7NyTzyfgfz0QYLtmuo";
        final static String customSearchEngineKey = "011355480889527342621:bznrw1ufoek";
        final static String searchURL = "https://www.googleapis.com/customsearch/v1?";
        private String query = "fortnite youtube";
        private int maxResults;
        public FxCustomGoogleSearch(String query,int maxResults) {
            this.query=query;
            this.maxResults=maxResults;
        }


        public Map<String, String> doSearch(int startFrom) {
            Map<String,String> pictures= new HashMap<String, String>();
            List<BufferedImage> images = new ArrayList<BufferedImage>();
            String url = CustomGoogleSearch.buildSearchString(query, startFrom, maxResults);
            String result = CustomGoogleSearch.search(url);
            JSONObject json = new JSONObject(result);
            JSONArray items = (JSONArray) json.get("items");
            for (int jsonPictureIndex = 0; jsonPictureIndex < items.length(); jsonPictureIndex++) {
                JSONObject item = items.getJSONObject(jsonPictureIndex);
                try {
                    JSONObject imageContext = item.getJSONObject("image");
                    String imageThumbnailLink = (String) imageContext.get("thumbnailLink");
                    String originalImageLink = (String) item.get("link");
                    pictures.put(imageThumbnailLink,originalImageLink);
                    //BufferedImage image = ImageIO.read(new URL(imageThumbnailLink));
                    //images.add(image);

                } catch (Exception e) {
                    continue;
                }}
                return pictures;
        }

    /*public static void main(String[] args) throws Exception {
        doSearch();
    }
*/
    }


