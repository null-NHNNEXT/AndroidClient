package com.goznauk.projectnull.app.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.goznauk.projectnull.app.Controller.ArticleEditFragment;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.Comment;
import com.goznauk.projectnull.app.Model.ArticleEditModel;
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

import java.util.ArrayList;


public class ArticleEditLayout extends BaseLayout implements ModelListener<ArticleEditModel>, View.OnClickListener {
    private ArticleListAdapter adapter;
    private Context context;
    private Button articleImageSettingButton;
    private Button articleSaveButton;
    private EditText articleEditTitle;
    private EditText articleEditContents;
    private ProgressDialog progressDialog;
    private Article article;

    public interface Listener {
        void onAddArticleImageButtonClicked();
        void onSaveButtonClicked(String titile, String content);
        void onArticleListRefresh();
    }

    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public ArticleEditLayout(Context context) {
        super(context, R.layout.fragment_articleedit);
        this.context = context;

        articleImageSettingButton = (Button)findViewById(R.id.article_edit_imagesetting_button);
        articleSaveButton = (Button)findViewById(R.id.article_edit_save_button);
        articleEditTitle = (EditText)findViewById(R.id.article_edit_title);
        articleEditContents = (EditText)findViewById(R.id.article_edit_content);
        articleImageSettingButton.setOnClickListener(this);
        articleSaveButton.setOnClickListener(this);

        if(article != null){
            articleEditTitle.setText(article.getTitle());
            articleEditContents.setText(article.getContents());
        }

    }

    public void setData(Bundle args){
        article = new Article(args.getString("articleId"),args.getString("title"),args.getString("content"), args.getString("penName"), args.getString("timeStamp"), args.getString("image"), new ArrayList<Comment>());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.article_edit_save_button:

                String articleTitle = articleEditTitle.getText().toString();
                String articleContent = articleEditContents.getText().toString();
                listener.onSaveButtonClicked(articleTitle, articleContent);
                break;
            case R.id.article_edit_imagesetting_button:
                break;
        }
    }

    @Override
    public void onModelUpdated(ArticleEditModel model) {

        switch (model.getStatus()) {
            case ArticleEditModel.LOADING :
                // TODO : Show loading animation or sth...
                break;

            case ArticleEditModel.DONE:
                listener.onArticleListRefresh();

                break;
            case ArticleEditModel.ERROR:
                //progressDialog.cancel();
                break;
        }

    }
}