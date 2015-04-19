package com.goznauk.projectnull.app.View;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.Model.SettingModel;
import com.goznauk.projectnull.app.R;

public class SettingLayout extends BaseLayout implements View.OnClickListener, ModelListener<SettingModel>{
    private Context context;

    //view
    private TextView nickname;
    private TextView profileTextView;
    private TextView showMyArticleTextView;
    private TextView showMyCommentTextView;
    private TextView pushSettingTextView;
    private Button profileImageSettingButton;
    private Button showMyArticleButton;
    private Button showMyCommentButton;
    private Button pushSettingButton;
    private ImageButton setNicknameButton;

    //typeface
    private Typeface SDCrayon;

    public interface Listener{
        public void onSetNicknameButtonClicked(ImageButton imageButon);
        public void onProfileImageSettingButtonClicked(Button button);
        public void onShowMyArticleButtonClicked(Button button);
        public void onShowMyCommentButtonClicked(Button button);
        public void onPushSettingButtonClicked(Button button);
    }

    private Listener listener;
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public SettingLayout(Context context){
        super(context, R.layout.fragment_setting);
        this.context = context;

        //view 찾아 변수에 넣기
        nickname = (TextView)findViewById(R.id.nickname);

        profileTextView = (TextView)findViewById(R.id.profile_image_setting_textview);
        showMyArticleTextView = (TextView)findViewById(R.id.show_my_article_textview);
        showMyCommentTextView = (TextView)findViewById(R.id.show_my_comment_textview);
        pushSettingTextView = (TextView)findViewById(R.id.push_setting_textview);

        setNicknameButton = (ImageButton)findViewById(R.id.set_nickname_button);
        profileImageSettingButton = (Button)findViewById(R.id.profile_image_setting_button);
        showMyArticleButton = (Button)findViewById(R.id.show_my_article_button);
        showMyCommentButton = (Button)findViewById(R.id.show_my_comment_button);
        pushSettingButton = (Button)findViewById(R.id.push_setting_button);

        //Button들 클릭이벤트리스너 붙이기
        setNicknameButton.setOnClickListener(this);
        profileTextView.setOnClickListener(this);
        showMyArticleButton.setOnClickListener(this);
        showMyCommentButton.setOnClickListener(this);
        pushSettingButton.setOnClickListener(this);


        //폰트 지정
        setTypeface(nickname, profileTextView, showMyArticleTextView, showMyCommentTextView,
                   pushSettingTextView, profileImageSettingButton, showMyArticleButton,
                   showMyCommentButton, pushSettingButton);
    }

    public void setTypeface(TextView nickname, TextView profileTextView,
                            TextView showMyArticleTextView, TextView showMyCommentTextView,
                            TextView pushSettingTextView,
                            Button profileImageSettingButton, Button showMyArticleButton,
                            Button showMyCommentButton, Button pushSettingButton){

        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(context);
        SDCrayon = typefaceFactory.getTypeface("SDCrayon");
        nickname.setTypeface(SDCrayon);
        profileTextView.setTypeface(SDCrayon);
        showMyArticleTextView.setTypeface(SDCrayon);
        showMyCommentTextView.setTypeface(SDCrayon);
        pushSettingTextView.setTypeface(SDCrayon);
        profileImageSettingButton.setTypeface(SDCrayon);
        showMyArticleButton.setTypeface(SDCrayon);
        showMyCommentButton.setTypeface(SDCrayon);
        pushSettingButton.setTypeface(SDCrayon);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.set_nickname_button :
                listener.onSetNicknameButtonClicked(setNicknameButton);
                break;
            case R.id.profile_image_setting_button :
                listener.onProfileImageSettingButtonClicked(profileImageSettingButton);
                break;
            case R.id.show_my_article_button :
                listener.onShowMyArticleButtonClicked(showMyArticleButton);
                break;
            case R.id.show_my_comment_button :
                listener.onShowMyCommentButtonClicked(showMyCommentButton);
                break;
            case R.id.push_setting_button :
                listener.onPushSettingButtonClicked(pushSettingButton);
                break;
        }
    }

    @Override
    public void onModelUpdated(SettingModel model) {

    }
}
