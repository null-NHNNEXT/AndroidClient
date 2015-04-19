package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.widget.Button;

import com.goznauk.projectnull.app.Model.ArticleEditModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;


public class ArticleEditLayout extends BaseLayout implements ModelListener<ArticleEditModel> {
    private ArticleListAdapter adapter;
    private Context context;
    private Button articleImageSettingButton;
    private Button articleSaveButton;

    public interface Listener {
        void onAddArticleImageButtonClicked();
        void onSaveButtonClicked();
    }

    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public ArticleEditLayout(Context context) {
        super(context, R.layout.fragment_articleedit);
        this.context = context;

    }


    @Override
    public void onModelUpdated(ArticleEditModel model) {

    }
}