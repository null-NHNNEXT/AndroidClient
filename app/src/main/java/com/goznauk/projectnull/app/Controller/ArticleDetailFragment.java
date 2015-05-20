package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.R;
import com.goznauk.projectnull.app.View.ArticleDetailLayout;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class ArticleDetailFragment extends Fragment implements ArticleDetailLayout.Listener {
    private ArticleDetailLayout layout;
    private ArticleDetailModel model;

    public static ArticleDetailFragment newInstance() {
        ArticleDetailFragment fragment = new ArticleDetailFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = new ArticleDetailLayout(getActivity());
        layout.setListener(this);

        Bundle args = getArguments();
        if (args == null) {
            return layout.getRootView();
        }

        model = new ArticleDetailModel(getActivity().getApplicationContext());
        model.setModelListener(layout);

        return layout.getRootView();
    }


    @Override
    public void onArticleEdit() {
        Bundle args = this.getArguments();


        callEditArticleFragment(args);
    }

    public void callEditArticleFragment(Bundle args){
        Fragment articleEditFragment = ArticleEditFragment.newInstance(ArticleEditFragment.EDITPOST);
        articleEditFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, articleEditFragment, "ArticleEdit");
        transaction.commit();
    }


    @Override
    public void onDelete() {
        Bundle args = this.getArguments();
        String articleId = args.getString("articleId");
        model.deleteArticle(articleId);



    }


    public void onArticleListRefresh() {
        // Fragment Transaction
        Log.i("test","hi");
        Fragment articleListFragment = new ArticleListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, articleListFragment, "articleList");
        // transaction.addToBackStack(null);
        transaction.commit();
    }
}
