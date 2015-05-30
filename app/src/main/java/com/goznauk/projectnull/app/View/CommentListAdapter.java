package com.goznauk.projectnull.app.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.goznauk.projectnull.app.Entity.Article;
import com.goznauk.projectnull.app.Entity.Comment;
import com.goznauk.projectnull.app.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Henry on 2015. 5. 29..
 */
public class CommentListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Comment> comments;
    private LayoutInflater mInflater;
    private Listener listener;

    public interface Listener{
        void onCommentDeleteButtonClicked();
    }

    public void setListner(Listener listener){
        this.listener = listener;
    }

    public CommentListAdapter(Context context, ArrayList<Comment> comments){
        this.mContext = context;
        this.comments = comments;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if(comments == null) {
            return 0;
        }
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_comment, null);
            holder = new ViewHolder();

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.penName = (TextView)convertView.findViewById(R.id.row_penName);
        holder.penName.setText(comments.get(position).getWriter());
        holder.comment = (TextView)convertView.findViewById(R.id.row_comment);
        holder.comment.setText(comments.get(position).getContents());
        setTypeface(holder.penName, holder.comment);



        return convertView;
    }

    public void setTypeface(TextView penName, TextView comment){
        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(mContext);
        Typeface SDCrayon = typefaceFactory.getTypeface("SDCrayon");
        penName.setTypeface(SDCrayon);
        comment.setTypeface(SDCrayon);
        }

    static class ViewHolder {
        TextView penName;
        TextView comment;
    }
}
