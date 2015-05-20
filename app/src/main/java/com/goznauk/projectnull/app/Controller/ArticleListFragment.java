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
import com.goznauk.projectnull.app.Model.ArticleListModel;
import com.goznauk.projectnull.app.R;
import com.goznauk.projectnull.app.View.ArticleListLayout;


//ArticleListFragment는 ArticleListLayout과 ArticleListModel의 Controller역할을 한다.
//ArtlcieListFargment는 ArticleListLayout(View)가 제공하는 인터페이스를 구현하게 된다.
public class ArticleListFragment extends Fragment implements ArticleListLayout.Listener {
    private ArticleListLayout layout;
    private ArticleListModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //layout을 생성하여 view를 보여준다.
        layout = new ArticleListLayout(getActivity());
        //layout에 controller를 listener로 등록한다.
        layout.setListener(this);

        //model을 생성한다.
        model = new ArticleListModel(getActivity().getApplicationContext());
        //model의 listener로 layout을 등록한다.
        //model과 view는 서로 모르기 때문에 controller에서 둘을 연결 시켜 주는 것임.
        model.setModelListener(layout);
        //model의 초기화
        model.fetch();


        //즉, view의 여러 event에 대한 구현은 controller에서 하고, controller를 listener로 등록해
        //controller가 구현한 메소드를 실행하게 된다.
        //한편, model은 modelListener로 layout(View)를 등록한다.
        //즉, view에서는 ModelListener의 메소드를 구현하고, model의 listener로 layout(view)를 등록한다.
        //listener는 실제 구현부를 가지고 있는 객체이며, 말 그대로 '듣고 있는 사람'이다.
        //controller는 view의 listener로, view가 어떤 이벤트를 받으면 그에 대한 처리 방법을 제공한다.
        //역시, model의 listener인 view역시 model이 어떤 변화가 있으면 그에 대한 처리를 하여 view를 뿌리게 된다.

        return layout.getRootView();
    }



    @Override
    public void onArticleClicked(Article clickedArticle) {
        Log.i("onItemClicked", "id : " + clickedArticle.getArticleId());
        // Fragment Transaction

        Fragment detailFragment = ArticleDetailFragment.newInstance();

        Bundle args = new Bundle();

        args.putString("articleId", clickedArticle.getArticleId());
        args.putString("title", clickedArticle.getTitle());
        args.putString("content", clickedArticle.getContents());
        args.putString("penName", clickedArticle.getPenName());
        args.putString("timeStamp", clickedArticle.getTimeStamp());
        args.putString("image", clickedArticle.getImage());
        detailFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, detailFragment, "Detail");
        // transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onArticleEditButtonClicked() {
        Fragment articleEditFragment = ArticleEditFragment.newInstance(ArticleEditFragment.NEWPOST);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, articleEditFragment, "ArticleEdit");
        transaction.commit();
    }

    @Override
    public void onSettingButtonClicked() {
        Fragment settingFragment = SettingFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, settingFragment, "Setting");
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
