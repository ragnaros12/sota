package com.company.vika;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_data);

        WebView view = findViewById(R.id.web);

        NetworkService.getInstance().getApi().getNews(getIntent().getStringExtra("html").substring(1)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Document document  = Jsoup.parse(response.body().string());
                    String unencodedHtml = document.select("head").html() + "\n" + document.select(".item-page").html();
                    String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                            Base64.NO_PADDING);
                    view.loadData(encodedHtml, "text/html", "base64");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}