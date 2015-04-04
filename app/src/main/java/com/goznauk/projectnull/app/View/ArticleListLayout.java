package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 3. 28..
 */
public class ArticleListLayout extends BaseLayout implements ModelListener<ArticleListModel>, ArticleListAdapter.Listener, View.OnClickListener {
    ArticleListAdapter adapter;
    Context context;

    public interface Listener {
        void onTest();
        void onArticleClicked(int articleId);
        void onRefreshButtonClicked();
        void onScrollLast();
    }

    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    private ArrayList<Article> articles;

    private Button refreshButton;
    private Button appendButton;
    private ListView listView;

    public ArticleListLayout(Context context) {
        super(context, R.layout.fragment_articlelist);
        this.context = context;

        Log.i("Layout", "init");

        listView = (ListView) findViewById(R.id.listview_test);
        appendButton = (Button) findViewById(R.id.fragment_list_append_btn);
        appendButton.setOnClickListener(this);
        refreshButton = (Button) findViewById(R.id.fragment_list_refresh_btn);
        refreshButton.setOnClickListener(this);
    }


    // OnClickListener impl
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_list_refresh_btn :
                if (listener != null) {
                    listener.onRefreshButtonClicked();
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.fragment_list_append_btn :
                if (listener != null) {
                    listener.onScrollLast();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    // ArticleListAdapter.Listener impl
    @Override
    public void onScrollLast() {
        listener.onScrollLast();
    }

    @Override
    public void onItemClicked(int articleId) {
        if (listener != null) {
            listener.onArticleClicked(articleId);
        }
    }


    // ModelListener<ArticleListModel> impl
    @Override
    public void onModelUpdated(ArticleListModel model) {
        switch (model.getStatus()) {
            case ArticleListModel.LOADING :
                // TODO : Show loading animation or sth...
                break;

            case ArticleListModel.DONE:
                if(articles == null || articles != model.getArticles()) {
                    articles = model.getArticles();

                    adapter = new ArticleListAdapter(context, articles);
                    adapter.setListener(this);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                break;

            case ArticleListModel.ERROR:

                break;
        }
    }
}
