package com.halodoc.testproject.problems.news.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halodoc.testproject.R;
import com.halodoc.testproject.problems.news.model.Hit;
import com.halodoc.testproject.problems.news.view.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Hit> articleList;
    private Context context;

    NewsAdapter(List<Hit> articleList,Context context) {
        setArticleList(articleList);
        this.context = context;
    }

    void setArticleList(List<Hit> articleList) {
        if (articleList == null) {
            this.articleList = new ArrayList<>();
        } else {
            this.articleList = articleList;
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.bind(articleList.get(i),i);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvUrl;
        CardView card;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            card = itemView.findViewById(R.id.card_view);
        }

        void bind(final Hit article, int position) {
            tvTitle.setText(article.getTitle());
            tvUrl.setText(article.getUrl());
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    i.putExtra("url",article.getUrl());

                    context.startActivity(i);
                }
            });

        }
    }
}
