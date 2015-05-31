package com.goznauk.projectnull.app.Controller;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.goznauk.projectnull.app.MQTT.MQTTservice;
import com.goznauk.projectnull.app.Model.SettingModel;
import com.goznauk.projectnull.app.R;
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
