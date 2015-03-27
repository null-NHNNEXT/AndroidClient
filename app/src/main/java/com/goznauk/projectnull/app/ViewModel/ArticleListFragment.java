package com.goznauk.projectnull.app.ViewModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.goznauk.projectnull.app.R;
import com.goznauk.projectnull.app.View.ArticleListLayout;

/**
 * Created by goznauk on 2015. 3. 27..
 */
public class ArticleListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ArticleListLayout layout = new ArticleListLayout(getActivity());
        layout.setListener(new ArticleListLayout.Listener() {
            @Override
            public void onTestClicked() {
                Toast.makeText(getActivity(), "TstBtn Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return layout.getRootView();
    }





}
