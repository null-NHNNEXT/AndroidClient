package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.R;
import com.goznauk.projectnull.app.View.ArticleListLayout;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class ArticleListFragment extends Fragment implements ArticleListLayout.Listener {
    private ArticleListLayout layout;
    private ArticleListModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = new ArticleListLayout(getActivity());
        layout.setListener(this);

        model = new ArticleListModel();
        model.setModelListener(layout);
        model.fetch();


        return layout.getRootView();
    }


    @Override
    public void onTest() {
        Toast.makeText(getActivity(), "TstBtn Clicked", Toast.LENGTH_SHORT).show();
        model.append();
    }


    @Override
    public void onArticleClicked(int articleId) {
        Log.i("onItemClicked", "id : " + articleId);
        // Fragment Transaction

        Fragment detailFragment = ArticleDetailFragment.newInstance(articleId);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, detailFragment, "Detail");
        // transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onRefreshButtonClicked() {
        model.fetch();
    }

    @Override
    public void onScrollLast() {
        model.append();
    }
}
