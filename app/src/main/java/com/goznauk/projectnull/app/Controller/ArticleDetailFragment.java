package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.Comment;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.R;
import com.goznauk.projectnull.app.View.ArticleDetailLayout;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class ArticleDetailFragment extends Fragment implements ArticleDetailLayout.Listener {
    private ArticleDetailLayout layout;
    private ArticleDetailModel model;
    private Article article;

    public static ArticleDetailFragment newInstance() {
        ArticleDetailFragment fragment = new ArticleDetailFragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Article article = this.getArguments().getParcelable("Article");
        layout = new ArticleDetailLayout(getActivity(), article);

        layout.setListener(this);


        model = new ArticleDetailModel(getActivity().getApplicationContext());
        model.setModelListener(layout);

        return layout.getRootView();
    }


    @Override
    public void onArticleEdit(String title, String content) {

        callEditArticleFragment(title, content);
    }

    public void callEditArticleFragment(String title, String content){
        Bundle contentInfo = new Bundle();
        contentInfo.putString("title", title);
        contentInfo.putString("content", content);
        Fragment articleEditFragment = ArticleEditFragment.newInstance(ArticleEditFragment.EDITPOST);
        articleEditFragment.setArguments(contentInfo);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, articleEditFragment, "ArticleEdit");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onDelete() {
        Bundle args = this.getArguments();
        String articleId = args.getString("articleId");
        model.deleteArticle(articleId);



    }

    @Override
    public void onCommentSave(String articleId, Comment comment) {
        model.saveComment(articleId, comment);
    }

    public void onArticleListRefresh() {
        // Fragment Transaction

        Fragment articleListFragment = new ArticleListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container, articleListFragment, "articleList");
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
