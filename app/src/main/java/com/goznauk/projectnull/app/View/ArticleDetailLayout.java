package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class ArticleDetailLayout extends BaseLayout implements ModelListener<ArticleDetailModel>, View.OnClickListener {

    private Context context;
    private Article article;
    public interface Listener {
        void onArticleEdit();
        void onDelete();
        void onArticleListRefresh();
    }

    private Listener listener;
    private TextView title;
    private TextView writer;
    private TextView timeStamp;
    private TextView content;
    private Button articleDeleteButton;
    private Button articleEditButton;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ArticleDetailLayout(Context context, Article article) {
        super(context, R.layout.fragment_articledetail);
        this.context = context;
        this.article = article;

        Log.i("Layout", "init");
        title = (TextView)findViewById(R.id.article_detail_title);
        writer = (TextView)findViewById(R.id.article_detail_writer);
        timeStamp = (TextView)findViewById(R.id.article_detail_timestamp);
        content = (TextView)findViewById(R.id.article_detail_content);
        articleDeleteButton = (Button)findViewById(R.id.article_edit_button);
        articleEditButton = (Button)findViewById(R.id.article_delete_button);

        title.setText(article.getTitle());
        writer.setText(article.getPenName());
        timeStamp.setText(article.getTimeStamp());
        content.setText(article.getContents());


        articleDeleteButton.setOnClickListener(this);
        articleEditButton.setOnClickListener(this);



        setTypeface(title, writer, timeStamp, content, articleDeleteButton, articleEditButton);

    }

    public void setTypeface(TextView title, TextView writer, TextView timeStamp, TextView content, Button articleDeleteButton, Button articleEditButton){
        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(context);
        Typeface SDCrayon = typefaceFactory.getTypeface("SDCrayon");

        title.setTypeface(SDCrayon);
        writer.setTypeface(SDCrayon);
        timeStamp.setTypeface(SDCrayon);
        content.setTypeface(SDCrayon);
        articleDeleteButton.setTypeface(SDCrayon);
        articleEditButton.setTypeface(SDCrayon);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.article_edit_button:
                listener.onArticleEdit();
                break;
            case R.id.article_delete_button:
                listener.onDelete();
                break;
        }
    }

    @Override
    public void onModelUpdated(ArticleDetailModel model) {
        switch (model.getStatus()) {
            case ArticleDetailModel.LOADING:
                // TODO : Show loading animation or sth...
                break;

            case ArticleDetailModel.DONE:


                listener.onArticleListRefresh();
                break;

            case ArticleDetailModel.ERROR:

                break;
        }
    }
}