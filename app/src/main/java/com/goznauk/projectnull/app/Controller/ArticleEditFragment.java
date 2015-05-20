package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Model.ArticleEditModel;
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.R;
import com.goznauk.projectnull.app.View.ArticleEditLayout;
import com.goznauk.projectnull.app.View.ArticleListLayout;



public class ArticleEditFragment extends Fragment implements ArticleEditLayout.Listener {
    private ArticleEditLayout layout;
    private ArticleEditModel model;
    public static final int NEWPOST = 0;
    public static final int EDITPOST = 1;
    private static int postStatus;


    //새 글을 쓰는 경우는 article를 받지 않고 생성
    public static ArticleEditFragment newInstance(int postStatus) {
        ArticleEditFragment fragment = new ArticleEditFragment();
        ArticleEditFragment.postStatus = postStatus;

        return fragment;
    }

//    //기존의 글을 수정하는 경우는 article를 입력 받고 생성
//    public static ArticleEditFragment newInstance(int EDITPOSTSTATUS) {
//        ArticleEditFragment fragment = new ArticleEditFragment();
//        postStatus = EDITPOSTSTATUS;
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = new ArticleEditLayout(getActivity());
        //????
        if(getArguments() != null)
            layout.setData(getArguments());

        layout.setListener(this);


        model = new ArticleEditModel(getActivity().getApplicationContext());
        model.setModelListener(layout);

        return layout.getRootView();
    }

    @Override
    public void onAddArticleImageButtonClicked() {

    }

    @Override
    public void onArticleListRefresh() {
        Log.i("test","Create POST succeed");
        // Fragment Transaction

        Fragment articleListFragment = new ArticleListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, articleListFragment, "articleList");
        // transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSaveButtonClicked(String title, String content) {

        if(postStatus == NEWPOST) model.fetch(postStatus, title, content);
        else model.fetch(postStatus, this.getArguments().getString("articleId"), title, content);
    }

}
