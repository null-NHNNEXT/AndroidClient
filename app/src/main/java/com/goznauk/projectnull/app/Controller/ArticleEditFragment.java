package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private static int STATUS;


    //새 글을 쓰는 경우는 articleId를 받지 않고 생성
    public static ArticleEditFragment newInstance(int NEWPOSTSTATUS) {
        ArticleEditFragment fragment = new ArticleEditFragment();
        STATUS = NEWPOSTSTATUS;
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    //기존의 글을 수정하는 경우는 articleId를 입력 받고 생성
    public static ArticleEditFragment newInstance(int EDITPOSTSTATUS, int articleId) {
        ArticleEditFragment fragment = new ArticleEditFragment();
        STATUS = EDITPOSTSTATUS;
        Bundle args = new Bundle();
        args.putInt("articleId", articleId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = new ArticleEditLayout(getActivity());
        layout.setListener(this);

        model = new ArticleEditModel();
        model.setModelListener(layout);
        model.fetch();

        return layout.getRootView();
    }

    @Override
    public void onAddArticleImageButtonClicked() {

    }

    @Override
    public void onSaveButtonClicked() {

    }

}
