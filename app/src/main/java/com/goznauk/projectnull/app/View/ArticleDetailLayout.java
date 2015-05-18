package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class ArticleDetailLayout extends BaseLayout implements ModelListener<ArticleDetailModel> {
    ArticleListAdapter adapter;
    Context context;

    public interface Listener {
        void onArticleEdit();
        void onDelete();
    }

    private Listener listener;
    private TextView title;
    private TextView writer;
    private TextView timeStamp;
    private TextView content;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ArticleDetailLayout(Context context) {
        super(context, R.layout.fragment_articledetail);
        this.context = context;

        Log.i("Layout", "init");
        title = (TextView)findViewById(R.id.article_detail_title);
        writer = (TextView)findViewById(R.id.article_detail_writer);
        timeStamp = (TextView)findViewById(R.id.article_detail_timestamp);
        content = (TextView)findViewById(R.id.article_detail_content);
        setTypeface(title, writer, timeStamp, content);

    }

    public void setTypeface(TextView title, TextView writer, TextView timeStamp, TextView content){
        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(context);
        Typeface SDCrayon = typefaceFactory.getTypeface("SDCrayon");

        title.setTypeface(SDCrayon);
        writer.setTypeface(SDCrayon);
        timeStamp.setTypeface(SDCrayon);
        content.setTypeface(SDCrayon);
    }

    @Override
    public void onModelUpdated(ArticleDetailModel model) {
        switch (model.getStatus()) {
            case ArticleDetailModel.LOADING:
                // TODO : Show loading animation or sth...
                break;

            case ArticleDetailModel.DONE:
                title.setText(model.getArticle().getTitle());
                writer.setText(model.getArticle().getPenName());
                timeStamp.setText((model.getArticle().getTimeStamp()));
                content.setText(model.getArticle().getContents());

                break;

            case ArticleDetailModel.ERROR:

                break;
        }
    }
}