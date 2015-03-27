package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.goznauk.projectnull.app.R;

/**
 * Created by goznauk on 2015. 3. 28..
 */
public class ArticleListLayout extends BaseLayout {

    public interface Listener {
        void onTestClicked();
    }

    private Listener listener;
    public void setListener(Listener listener) {
        this.listener = listener;
    }



    private Button testButton;

    public ArticleListLayout(Context context) {
        super(context, R.layout.fragment_articlelist);

        testButton = (Button) findViewById(R.id.fragment_list_test_btn);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onTestClicked();
                }
            }
        });
    }


}
