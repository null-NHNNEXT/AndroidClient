package com.goznauk.projectnull.app.View;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.goznauk.projectnull.app.Entity.Comment;
import com.goznauk.projectnull.app.Model.ArticleDetailModel;
import com.goznauk.projectnull.app.Model.ModelListener;
import com.goznauk.projectnull.app.Model.SettingModel;
import com.goznauk.projectnull.app.R;

import java.io.File;

public class SettingLayout extends BaseLayout implements View.OnClickListener, ModelListener<SettingModel>{
    private Context context;

    //view
    private TextView nickname;
    private TextView profileTextView;
    private TextView pushSettingTextView;
    private Button profileImageSettingButton;
    private Button pushSettingButton;
    private ImageButton profileImageButton;


    private TextView myPhotoTextView;

    private Activity activity;

    //typeface
    private Typeface SDCrayon;

    public interface Listener{
        public void onSetNicknameButtonClicked(ImageButton imageButon);
        public void onProfileImageSettingButtonClicked();
        public void onShowMyArticleButtonClicked(Button button);
        public void onShowMyCommentButtonClicked(Button button);
        public void onSubscribeButtonClicked();
        public void onProfileImageClicked(ImageButton profileImageButton);
        public Context getContext();
    }

    private Listener listener;
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public SettingLayout(Context context, Activity activity){
        super(context, R.layout.fragment_setting);
        this.context = context;
        this.activity = activity;

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
        profileImageButton = (ImageButton)findViewById(R.id.profile_image);


        //profile image 삽입
        insertProfileImage();


        //Button들 클릭이벤트리스너 붙이기
        //setNicknameButton.setOnClickListener(this);
        profileTextView.setOnClickListener(this);
//        showMyArticleButton.setOnClickListener(this);
//        showMyCommentButton.setOnClickListener(this);
        pushSettingButton.setOnClickListener(this);
        profileImageButton.setOnClickListener(this);
        profileImageSettingButton.setOnClickListener(this);

        SharedPreferences pref = context.getSharedPreferences("push", Context.MODE_PRIVATE);
        String push = pref.getString("push","false");
        if(push == "true") pushSettingButton.setText("멈추기");
        else pushSettingButton.setText("받기");



        //폰트 지정
        setTypeface(profileTextView, pushSettingTextView, profileImageSettingButton, pushSettingButton, myPhotoTextView);
    }

    public void insertProfileImage(){
        SharedPreferences pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        final String fileName = pref.getString("UUID","nothing");
        String img_path = context.getFilesDir().getPath() + "/" + fileName;
        File img_load_path = new File(img_path);
        if(img_load_path.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(img_path);
            profileImageButton.setImageBitmap(bitmap);
        }
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
                listener.onProfileImageSettingButtonClicked();
                break;
            case R.id.profile_image :
                Log.i("test", "클릭됨");
                listener.onProfileImageClicked(profileImageButton);

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

    private ProgressDialog progressDialog;

    @Override
    public void onModelUpdated(SettingModel model) {
        switch (model.getStatus()) {
            case SettingModel.LOADING:
                // TODO : Show loading animation or sth...

                Log.i("llll", "1");
                progressDialog = ProgressDialog.show(listener.getContext(), "", "Loading...");
                Log.i("llll", "2");

                break;

            case SettingModel.DONE:
                Log.i("llll", "3");
              //  Bitmap bitmap = BitmapFactory.decodeFile(model.getProfilePhoto());
               // profileImageButton.setImageBitmap(bitmap);


                insertProfileImage();
                progressDialog.cancel();

                Log.i("llll", "4");


                break;

            case SettingModel.ERROR:
                progressDialog.cancel();

                break;
        }
    }
}
