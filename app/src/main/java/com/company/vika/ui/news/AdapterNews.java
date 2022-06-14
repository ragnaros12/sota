package com.company.vika.ui.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.R;
import com.company.vika.WebData;
import com.company.vika.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.NewsItem> {
    List<News> newsList;
    Context context;
    LayoutInflater layoutInflater;

    public AdapterNews(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NewsItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsItem(layoutInflater.inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItem holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.desc.setText(news.getDescription());
        Picasso.get().load(news.getImage()).fit()
                .centerCrop().into(holder.imageView);
        holder.itemView.findViewById(R.id.clicked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, WebData.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("html", news.getData());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsItem extends RecyclerView.ViewHolder{
        TextView title, desc;
        ImageView imageView;

        public NewsItem(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.header_news);
            desc = itemView.findViewById(R.id.description_news);
            imageView = itemView.findViewById(R.id.image_news);
        }
    }
}
