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
import com.goznauk.projectnull.app.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ArticleListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public interface Listener {
        void onItemClicked(Article clickedArticle);
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_article, null);
            holder = new ViewHolder();

            //row의 item들에 대한 click listener 구현
            holder.articleListTitle = (TextView) convertView.findViewById(R.id.article_list_title);
            holder.articleListTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(articles.get(position));
                }
            });
            holder.articleListContent = (TextView) convertView.findViewById(R.id.article_list_content);
            holder.articleListContent.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(articles.get(position));
                }
            });
            holder.articleListWriter = (TextView) convertView.findViewById(R.id.article_list_writer);
            holder.articleListWriter.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(articles.get(position));
                }
            });
            holder.articleListTimeStamp = (TextView) convertView.findViewById(R.id.article_list_timestamp);
            holder.articleListTimeStamp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(articles.get(position));
                }
            });
//            holder.articleListImage = (ImageButton) convertView.findViewById(R.id.article_list_image);
//            holder.articleListImage.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClicked(articles.get(position));
//                }
//            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WeakReference<ImageView> imageViewReference = new WeakReference<ImageView>(holder.articleListImage);

        holder.articleListTitle.setText(articles.get(position).getTitle());
        holder.articleListContent.setText(articles.get(position).getContents());
        holder.articleListWriter.setText(articles.get(position).getPenName());
        holder.articleListTimeStamp.setText(articles.get(position).getTimeStamp());
        setTypeface(holder.articleListTitle, holder.articleListContent,
                            holder.articleListWriter, holder.articleListTimeStamp);

//        //사진 불러오기
//        String imagePath = mContext.getFilesDir() + "/" + articles.get(position).getImage();
//        File loadedImage = new File(imagePath);
//
//        //각 row의 사진 갱신해주기
//        if(loadedImage.exists()){
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            holder.articleListImage.setImageBitmap(bitmap);
//        }else{
//            /*
//                프로필 사진이 없는 경우의 default 이미지 처리
//             */
//        }


        return convertView;
    }

    public void setTypeface(TextView title, TextView content, TextView writer, TextView timeStamp){
        TypefaceFactory typefaceFactory = TypefaceFactory.getTypefaceFactory(mContext);
        Typeface SDCrayon = typefaceFactory.getTypeface("SDCrayon");
        title.setTypeface(SDCrayon);
        content.setTypeface(SDCrayon);
        writer.setTypeface(SDCrayon);
        timeStamp.setTypeface(SDCrayon);
    }


    static class ViewHolder {
        String articleId;
        ImageButton articleListImage;
        TextView articleListTitle;
        TextView articleListContent;
        TextView articleListWriter;
        TextView articleListTimeStamp;
    }
}
