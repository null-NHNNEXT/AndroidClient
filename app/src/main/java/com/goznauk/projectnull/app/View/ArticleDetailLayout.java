package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.Comment;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.R;

/**
 * Created by goznauk on 2015. 4. 4..
 */
public class ArticleDetailLayout extends BaseLayout implements ModelListener<ArticleDetailModel>, View.OnClickListener, CommentListAdapter.Listener {

    private Context context;
    private Article article;

    public interface Listener {
        void onArticleEdit(String title, String content);
        void onDelete();
        void onArticleListRefresh();
        void onCommentSave(String articleId, Comment comment);
    }

    private Listener listener;
    private TextView title;
    private TextView writer;
    private TextView timeStamp;
    private TextView content;
    private Button articleDeleteButton;
    private Button articleEditButton;
    private ListView commentListView;
    private Button commentSaveButton;
    private EditText commentEditText;
    private CommentListAdapter commentListAdapter;

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
        commentSaveButton = (Button)findViewById(R.id.comment_save_button);
        commentEditText = (EditText)findViewById(R.id.comment_edit_text);

        commentListView = (ListView)findViewById(R.id.comments_list_view);
        commentListAdapter = new CommentListAdapter(context, article.getComments());
        commentListView.setAdapter(commentListAdapter);


        title.setText(article.getTitle());
        writer.setText(article.getPenName());
        timeStamp.setText(article.getTimeStamp());
        content.setText(article.getContents());


        articleDeleteButton.setOnClickListener(this);
        articleEditButton.setOnClickListener(this);
        commentSaveButton.setOnClickListener(this);


        setTypeface(title, writer, timeStamp, content, articleDeleteButton, articleEditButton, commentSaveButton);

    }

    public void setTypeface(TextView title, TextView writer, TextView timeStamp, TextView content, Button articleDeleteButton, Button articleEditButton, Button commentSaveButton){
        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(context);
        Typeface SDCrayon = typefaceFactory.getTypeface("SDCrayon");

        title.setTypeface(SDCrayon);
        writer.setTypeface(SDCrayon);
        timeStamp.setTypeface(SDCrayon);
        content.setTypeface(SDCrayon);
        articleDeleteButton.setTypeface(SDCrayon);
        articleEditButton.setTypeface(SDCrayon);
        commentSaveButton.setTypeface(SDCrayon);
    }

    @Override
    public void onCommentDeleteButtonClicked() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.article_edit_button:
                listener.onArticleEdit(title.getText().toString(), content.getText().toString());
                break;
            case R.id.article_delete_button:
                listener.onDelete();
                break;
            case R.id.comment_save_button:
                listener.onCommentSave(article.getArticleId(),new Comment(commentEditText.getText().toString()));
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

            case ArticleDetailModel.COMMENT_DONE:
                SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
                String penName = pref.getString("penName","nothing");
                article.getComments().add(new Comment(penName, commentEditText.getText().toString()));
                commentListAdapter.notifyDataSetChanged();
                break;
            case ArticleDetailModel.COMMENT_ERROR:
                break;
        }
    }
}