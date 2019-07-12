package com.halodoc.testproject.problems.news.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.halodoc.testproject.R;
import com.halodoc.testproject.problems.news.model.Hit;
import com.halodoc.testproject.problems.news.model.NewsResponse;
import com.halodoc.testproject.problems.news.presenter.NewsPresenter;
import com.halodoc.testproject.problems.news.util.DatabaseHelper;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements com.halodoc.testproject.problems.news.presenter.NewsPresenter.View {

    private NewsPresenter newsPresenter;
    private NewsAdapter newsAdapter;
    private EditText etSearch;
    private Button btnReload;
    private ProgressBar progressBar;
    private LinearLayout errorLayout,emptyLayout;
    private TextView tvErrorNews,tvSearchGO;
    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsPresenter = new NewsPresenter(this);
        db = new DatabaseHelper(NewsActivity.this);

        String search = getIntent().getStringExtra("category");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.search_edit);
        btnReload = findViewById(R.id.btn_reload);
        progressBar = findViewById(R.id.circular_progress_bar);
        errorLayout = findViewById(R.id.error_layout);
        emptyLayout = findViewById(R.id.empty_layout);
        tvErrorNews = findViewById(R.id.tv_error_news);
        tvSearchGO = findViewById(R.id.search_go_button);
        newsAdapter = new NewsAdapter(null, getBaseContext());
        recyclerView.setAdapter(newsAdapter);

        newsPresenter.getEverything(search);

        tvSearchGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etSearch.getText().toString().equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    newsAdapter.setArticleList(null);
                    newsPresenter.getEverything(etSearch.getText().toString());
                    etSearch.setText("");
                }
                else {
                    Toast.makeText(NewsActivity.this,"Please fill the field",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                newsAdapter.setArticleList(null);
                errorLayout.setVisibility(View.GONE);
                if (!etSearch.getText().toString().equals(""))
                    newsPresenter.getEverything(etSearch.getText().toString());
                else
                    newsPresenter.getEverything("android");
            }
        });
    }


    @Override
    public void onSuccessGetNews(List<Hit> articleList) {
        Log.i("article","article list length "+articleList.size());
        if (articleList.size()>0) {
            errorLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.GONE);


            // saving to local DB
            NewsResponse nr = new NewsResponse(false,articleList,0,0,0,0,"",0,"");
            db.createNews(nr);

        }else{
            emptyLayout.setVisibility(View.VISIBLE);
        }

        progressBar.setVisibility(View.GONE);

        newsAdapter.setArticleList(articleList);
        newsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onErrorGetNews(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();

        if (db.getNewsList().size()>0){
            showDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }




    public void showDialog()  {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);

        builder.setMessage("Fail to load data, \nplease check your network \ndo you want to load data from local database ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                errorLayout.setVisibility(View.GONE);
                newsAdapter.setArticleList(db.getNewsList());
                newsAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
