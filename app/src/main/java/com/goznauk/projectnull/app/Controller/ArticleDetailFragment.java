package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.View.ArticleDetailLayout;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class ArticleDetailFragment extends Fragment implements ArticleDetailLayout.Listener {
    private ArticleDetailLayout layout;
    private ArticleDetailModel model;

    public static ArticleDetailFragment newInstance(int articleId) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putInt("articleId", articleId);
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

        model = new ArticleDetailModel(args.getInt("articleId"));
        model.setModelListener(layout);
        model.fetch();

        return layout.getRootView();
    }


    @Override
    public void onTest() {

    }
}
