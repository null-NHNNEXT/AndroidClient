package com.goznauk.projectnull.app.Controller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.goznauk.projectnull.app.MQTT.MQTTservice;
import com.goznauk.projectnull.app.Model.SettingModel;
import com.goznauk.projectnull.app.View.SettingLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Henry on 2015. 4. 10..
 */
public class SettingFragment extends Fragment implements SettingLayout.Listener{
    private SettingModel model;
    private SettingLayout layout;
    private ImageButton profileImage;

    @Override
    public Context getContext() {
        return this.getActivity();
    }

    public static SettingFragment newInstance(){
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        layout = new SettingLayout(getActivity().getApplicationContext(),getActivity());
        layout.setListener(this);

        model = new SettingModel(getActivity().getApplicationContext());
        model.setModelListener(layout);
        model.fetch();

        return layout.getRootView();
    }




    @Override
    public void onSetNicknameButtonClicked(ImageButton button) {

    }

    @Override
    public void onProfileImageSettingButtonClicked() {
        model.upload(filePath);
    }

    @Override
    public void onShowMyArticleButtonClicked(Button button) {

    }

    @Override
    public void onShowMyCommentButtonClicked(Button button) {

    }

    //push

    private Messenger service = null;
    private final Messenger serviceHandler = new Messenger(new ServiceHandler());
    private IntentFilter intentFilter = null;
    private PushReceiver pushReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.MQTT.PushReceived");
        pushReceiver = new PushReceiver();
        getActivity().getApplicationContext().registerReceiver(pushReceiver, intentFilter, null, null);

        getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), MQTTservice.class));




    }


    private static final int REQUEST_PHOTO_ALBUM = 1;
    String filePath;
    @Override
    public void onProfileImageClicked(ImageButton profileImage) {
        this.profileImage = profileImage;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_PHOTO_ALBUM) {
            if(resultCode== Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    Uri uri = getRealPathUri(data.getData());
                    filePath = uri.toString();


                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    profileImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Uri getRealPathUri(Uri uri){
        Uri filePathUri = uri;
        if(uri.getScheme().toString().compareTo("content") == 0){
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            if(cursor.moveToFirst()){
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(column_index));
            }
        }
        return filePathUri;
    }
//    public String getImageNameToUri(Uri data) {
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getActivity().getContentResolver().query(data, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//        cursor.moveToFirst();
//
//        String imgPath = cursor.getString(column_index);
//        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
//
//        return imgName;
//    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getApplicationContext().bindService(new Intent(getActivity().getApplicationContext(), MQTTservice.class), serviceConnection, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getApplicationContext().registerReceiver(pushReceiver, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().getApplicationContext().unregisterReceiver(pushReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
       // getActivity().getApplicationContext().unbindService(serviceConnection);

    }

    public class PushReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent i)
        {
            String topic = i.getStringExtra(MQTTservice.TOPIC);
            String message = i.getStringExtra(MQTTservice.MESSAGE);
            Toast.makeText(context, "Push message received : " + message, Toast.LENGTH_LONG).show();
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder)
        {
            service = new Messenger(binder);
            Bundle data = new Bundle();
            //data.putSerializable(MQTTservice.CLASSNAME, MainActivity.class);
            data.putCharSequence(MQTTservice.INTENTNAME, "com.example.MQTT.PushReceived");
            Message msg = Message.obtain(null, MQTTservice.REGISTER);
            msg.setData(data);
            msg.replyTo = serviceHandler;
            try
            {
                service.send(msg);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
        }
    };

    public void onSubscribeButtonClicked(){

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String topic = "board" + pref.getString("boardId","nothing");
                if (topic != null && topic.isEmpty() == false)
                {
                    Bundle data = new Bundle();
                    data.putCharSequence(MQTTservice.TOPIC, topic);
                    Message msg = Message.obtain(null, MQTTservice.SUBSCRIBE);
                    msg.setData(data);
                    msg.replyTo = serviceHandler;
                    try
                    {
                        service.send(msg);
                    }
                    catch (RemoteException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(),"Subscribe failed with exception:" + e.getMessage(), Toast.LENGTH_SHORT);
                    }
                }

    }

    class ServiceHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MQTTservice.SUBSCRIBE: 	break;
                case MQTTservice.PUBLISH:		break;
                case MQTTservice.REGISTER:		break;
                default:
                    super.handleMessage(msg);
                    return;
            }

            Bundle b = msg.getData();
            if (b != null)
            {
                Boolean status = b.getBoolean(MQTTservice.STATUS);
                if (status == false)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_SHORT);
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Succeed", Toast.LENGTH_SHORT);
                }
            }
        }
    }

}
