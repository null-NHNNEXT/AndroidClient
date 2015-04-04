package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.R;

import java.util.ArrayList;

/**
 * Created by goznauk on 2015. 4. 1..
 */
public class ArticleListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public interface Listener {
        void onScrollLast();
        void onItemClicked(int articleId);
    }

    private Listener listener;

    private ArrayList<Article> articles;

    public ArticleListAdapter(Context context, ArrayList<Article> articles) {
        this.mContext = context;
        this.articles = articles;
        mInflater = LayoutInflater.from(context);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    @Override
    public int getCount() {
        if(articles == null) {
            return 0;
        }
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return articles.get(position).getArticleId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_article, null);
            holder = new ViewHolder();
            holder.articleId = articles.get(position).getArticleId();
            holder.testTextView = (TextView) convertView.findViewById(R.id.item_article_tv_test);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.testTextView.setText(articles.get(position).getTestText());
        holder.testTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(articles.get(position).getArticleId());
            }
        });

        return convertView;
    }



    static class ViewHolder {
        int articleId;
        TextView testTextView;
    }
}
