package com.goznauk.projectnull.app.View;


import android.content.Context;
import android.content.SharedPreferences;
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
    private TextView pushSettingTextView;
    private Button profileImageSettingButton;
    private Button pushSettingButton;

    private TextView myPhotoTextView;

    //typeface
    private Typeface SDCrayon;

    public interface Listener{
        public void onSetNicknameButtonClicked(ImageButton imageButon);
        public void onProfileImageSettingButtonClicked(Button button);
        public void onShowMyArticleButtonClicked(Button button);
        public void onShowMyCommentButtonClicked(Button button);
        public void onSubscribeButtonClicked();
    }

    private Listener listener;
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public SettingLayout(Context context){
        super(context, R.layout.fragment_setting);
        this.context = context;

        //view 찾아 변수에 넣기

        profileTextView = (TextView)findViewById(R.id.profile_image_setting_textview);
//        showMyArticleTextView = (TextView)findViewById(R.id.show_my_article_textview);
//        showMyCommentTextView = (TextView)findViewById(R.id.show_my_comment_textview);
        pushSettingTextView = (TextView)findViewById(R.id.push_setting_textview);

        profileImageSettingButton = (Button)findViewById(R.id.profile_image_setting_button);
//        showMyArticleButton = (Button)findViewById(R.id.show_my_article_button);
//        showMyCommentButton = (Button)findViewById(R.id.show_my_comment_button);
        pushSettingButton = (Button)findViewById(R.id.push_setting_button);
        myPhotoTextView = (TextView)findViewById(R.id.my_profile_textview);

        //Button들 클릭이벤트리스너 붙이기
        //setNicknameButton.setOnClickListener(this);
        profileTextView.setOnClickListener(this);
//        showMyArticleButton.setOnClickListener(this);
//        showMyCommentButton.setOnClickListener(this);
        pushSettingButton.setOnClickListener(this);

        SharedPreferences pref = context.getSharedPreferences("push", Context.MODE_PRIVATE);
        String push = pref.getString("push","false");
        if(push == "true") pushSettingButton.setText("멈추기");
        else pushSettingButton.setText("받기");



        //폰트 지정
        setTypeface(profileTextView, pushSettingTextView, profileImageSettingButton, pushSettingButton, myPhotoTextView);
    }

    public void setTypeface(TextView profileTextView,
                            TextView pushSettingTextView,
                            Button profileImageSettingButton, Button pushSettingButton, TextView myPhotoTextView){

        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(context);
        SDCrayon = typefaceFactory.getTypeface("SDCrayon");
        profileTextView.setTypeface(SDCrayon);
        pushSettingTextView.setTypeface(SDCrayon);
        profileImageSettingButton.setTypeface(SDCrayon);
        pushSettingButton.setTypeface(SDCrayon);
        myPhotoTextView.setTypeface(SDCrayon);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.profile_image_setting_button :
                listener.onProfileImageSettingButtonClicked(profileImageSettingButton);
                break;
//            case R.id.show_my_article_button :
//                listener.onShowMyArticleButtonClicked(showMyArticleButton);
//                break;
//            case R.id.show_my_comment_button :
//                listener.onShowMyCommentButtonClicked(showMyCommentButton);
//                break;
            case R.id.push_setting_button :
                SharedPreferences pref = context.getSharedPreferences("push", Context.MODE_PRIVATE);
                String push = pref.getString("push","false");

                if(push == "true") {
                    pushSettingButton.setText("멈추기");
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("push", "false");
                    editor.commit();
                }else{
                    pushSettingButton.setText("받기");
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("push", "true");
                    editor.commit();
                }
                listener.onSubscribeButtonClicked();
                break;
        }
    }

    @Override
    public void onModelUpdated(SettingModel model) {

    }
}
