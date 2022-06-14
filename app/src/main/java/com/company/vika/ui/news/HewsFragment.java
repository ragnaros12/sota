package com.company.vika.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.NetworkService;
import com.company.vika.databinding.FragmentNewsBinding;
import com.company.vika.models.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView listNews = binding.listNews;


        NetworkService.getInstance().getApi().getNews(0).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        List<News> news = new ArrayList<>();
                        Document document = Jsoup.parse(response.body().string());
                        Element el = document.select(".blog").get(0);
                        for (Element element : el.select(".art-post-inner")){
                            if(!element.text().equals("")) {
                                try {
                                    String name = element.select("h2").get(0).text().toLowerCase(Locale.ROOT);
                                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                                    name = name.length() > 50 ? name.substring(0,49) + "..." : name;

                                    String desc = element.select("p").text();
                                    desc = desc.length() > 60 ? desc.substring(0,59) + "..." : desc;
                                    String image = "https://www.profsota.ru" + element.select(
                                            "img"
                                    ).get(0).attr("src");
                                    news.add(new News(name, desc, image, element.select(".readon.art-button").attr("href")));
                                }
                                catch (Exception e){

                                }
                            }
                        }

                        AdapterNews adapterNews = new AdapterNews(news, getContext());

                        listNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            int pages = 0;
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                int lastvisibleitemposition = ((LinearLayoutManager)listNews.getLayoutManager()).findLastVisibleItemPosition();
                                if (lastvisibleitemposition == adapterNews.getItemCount() - 1) {
                                    pages += 9;
                                    NetworkService.getInstance().getApi().getNews(pages).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if(response.code() == 200){
                                                try {
                                                    Document document = Jsoup.parse(response.body().string());
                                                    Element el = document.select(".blog").get(0);
                                                    for (Element element : el.select(".art-post-inner")){
                                                        if(!element.text().equals("")) {
                                                            try {
                                                                String name = element.select("h2").get(0).text().toLowerCase(Locale.ROOT);
                                                                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                                                                name = name.length() > 50 ? name.substring(0,49) + "..." : name;

                                                                String desc = element.select("p").text();
                                                                desc = desc.length() > 60 ? desc.substring(0,59) + "..." : desc;
                                                                String image = "https://www.profsota.ru" + element.select(
                                                                        "img"
                                                                ).get(0).attr("src");
                                                                news.add(new News(name, desc, image, element.html()));
                                                            }
                                                            catch (Exception e){

                                                            }
                                                        }
                                                    }
                                                    adapterNews.newsList = news;
                                                    adapterNews.notifyDataSetChanged();
                                                }
                                                catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }
                                            else{
                                                Toast.makeText(getContext(), response.code() + "", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }
                        });

                        listNews.setAdapter(adapterNews);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getContext(), response.code() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}