package at.ac.univie.hci.rhymr00908843;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private String originalWord;
    private Context activityContext;

    public DownloadTask(String originalWord, Context activityContext) {
        this.originalWord = originalWord;
        this.activityContext = activityContext;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection request = null;

        try{
            url = new URL(urls[0]);
            request = (HttpURLConnection) url.openConnection();
            InputStream in = request.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(reader);

            JsonArray jsonArray =  new JsonArray();
            jsonArray = root.getAsJsonArray();
            result = jsonArray.toString();
            return result;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{
            JsonArray jsonArray = new Gson().fromJson(s, JsonArray.class);
            ArrayList<String> wordList = new ArrayList<>();
            for(JsonElement jElement : jsonArray) {
                String rhymeWord = jElement.getAsJsonObject().get("word").getAsString();
                wordList.add(rhymeWord);
            }

            Intent intent = new Intent(activityContext, ResultActivity.class);
            intent.putExtra("OriginalWord", originalWord);
            intent.putExtra("WordList", wordList);
            activityContext.startActivity(intent);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}