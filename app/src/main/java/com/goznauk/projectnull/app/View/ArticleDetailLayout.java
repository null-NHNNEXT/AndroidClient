package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.Model.SingleArticleModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class ArticleDetailLayout extends BaseLayout implements View.OnClickListener, ModelListener<SingleArticleModel> {
    ArticleListAdapter adapter;
    Context context;

    public interface Listener {
        void onTest();
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    private Article article;

    private Button testButton;
    private TextView testTextView;

    public ArticleDetailLayout(Context context) {
        super(context, R.layout.fragment_articledetail);
        this.context = context;

        Log.i("Layout", "init");


        testButton = (Button) findViewById(R.id.fragment_detail_test_btn);
        testButton.setOnClickListener(this);
        testTextView = (TextView) findViewById(R.id.fragment_detail_test_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_detail_test_btn:
                if (listener != null) {
                    listener.onTest();
                }
                break;
        }
    }

    @Override
    public void onModelUpdated(SingleArticleModel model) {
        switch (model.getStatus()) {
            case SingleArticleModel.LOADING:
                // TODO : Show loading animation or sth...
                break;

            case SingleArticleModel.DONE:
                testTextView.setText("this article's ID: " + model.getArticle().getArticleId());
                break;

            case SingleArticleModel.ERROR:

                break;
        }
    }
}