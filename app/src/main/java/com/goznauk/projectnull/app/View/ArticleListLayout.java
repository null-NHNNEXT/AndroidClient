package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

import java.util.ArrayList;


public class ArticleListLayout extends BaseLayout implements View.OnClickListener,
        ModelListener<ArticleListModel>, ArticleListAdapter.Listener, AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener{
    ArticleListAdapter adapter;
    Context context;
    Typeface SDCrayon;


    public interface Listener {
        void onArticleClicked(Article article);
        void onRefreshButtonClicked();
        void onScrollLast();
        void onArticleEditButtonClicked();
        void onSettingButtonClicked();
    }

    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    private ArrayList<Article> articles;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private Button editArticleButton;
    private Button settingButton;

    public ArticleListLayout(Context context) {
        //BaseLayout(parant 객체)의 생성자를 호출하여 view를 화면에 inflate한다.
        super(context, R.layout.fragment_articlelist);
        this.context = context;

        Log.i("Layout", "init");

        listView = (ListView) findViewById(R.id.article_list_listview);
        editArticleButton = (Button)findViewById(R.id.article_list_edit_button);
        settingButton = (Button)findViewById(R.id.article_list_setting_button);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        editArticleButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);

        //버튼 글씨체 변경
        setTypeface(editArticleButton, settingButton);
        listView.setOnScrollListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    public void setTypeface(Button editArticleButton, Button settingButton){
        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(context);
        SDCrayon = typefaceFactory.getTypeface("SDCrayon");
        editArticleButton.setTypeface(SDCrayon);
        settingButton.setTypeface(SDCrayon);
    }

//    // OnClickListener impl
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.fragment_list_refresh_btn :
//                if (listener != null) {
//                    //refresh버튼 클릭 이벤트를 구현하는 listener(controller)의 메소드 호출
//                    listener.onRefreshButtonClicked();
//                }
//                break;
//            case R.id.fragment_list_append_btn :
//                if (listener != null) {
//                    //append버튼 클릭 이벤트를 구현하는 listener(controller)의 메소드 호출
//                    listener.onScrollLast();
//                }
//                break;
//        }
//    }

    // ArticleListAdapter.Listener impl


    @Override
    public void onItemClicked(Article clickedArticle) {
        listener.onArticleClicked(clickedArticle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.article_list_edit_button:
                Log.i("test", "여기까지");
                listener.onArticleEditButtonClicked();
                break;
            case R.id.article_list_setting_button:
                listener.onSettingButtonClicked();
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


    private int preLast;
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        switch(view.getId()){
            case R.id.article_list_listview:
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount){
                    if(preLast != lastItem){
                        preLast = lastItem;
                        listener.onScrollLast();
                    }
                }
        }
    }

    @Override
    public void onRefresh() {
        Log.i("test","Refreshing");
        listener.onRefreshButtonClicked();
        swipeRefreshLayout.setRefreshing(false);
    }

    // ModelListener<ArticleListModel> impl
    @Override
    public void onModelUpdated(ArticleListModel model) {
        switch (model.getStatus()) {
            case ArticleListModel.LOADING :
                // TODO : Show loading animation or sth...
                break;

            case ArticleListModel.DONE:
                //model에서 정상적으로 네트워크를 통해 데이터를 가져 왔을 경우 데이터를 가져옴.
                if(model.getArticles() != null) {

                    articles = model.getArticles();
                    //어뎁터 생성
                    adapter = new ArticleListAdapter(context, articles);
                    //어뎁터의 listener로 layout을 등록함
                    adapter.setListener(this);
                    listView.setAdapter(adapter);
                }
                //어뎁터 갱신
                adapter.notifyDataSetChanged();
                break;

            case ArticleListModel.ERROR:

                break;
        }
    }
}
