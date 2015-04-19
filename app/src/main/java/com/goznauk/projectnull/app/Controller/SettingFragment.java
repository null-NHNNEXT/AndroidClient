package com.goznauk.projectnull.app.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.goznauk.projectnull.app.Model.SettingModel;
import com.goznauk.projectnull.app.View.SettingLayout;

/**
 * Created by Henry on 2015. 4. 10..
 */
public class SettingFragment extends Fragment implements SettingLayout.Listener{
    private SettingModel model;
    private SettingLayout layout;


    public static SettingFragment newInstance(){
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        layout = new SettingLayout(getActivity());
        layout.setListener(this);

        model = new SettingModel();
        model.setModelListener(layout);
        model.fetch();

        return layout.getRootView();
    }

    @Override
    public void onSetNicknameButtonClicked(ImageButton button) {

    }

    @Override
    public void onProfileImageSettingButtonClicked(Button button) {

    }

    @Override
    public void onShowMyArticleButtonClicked(Button button) {

    }

    @Override
    public void onShowMyCommentButtonClicked(Button button) {

    }

    @Override
    public void onPushSettingButtonClicked(Button button) {

    }
}
