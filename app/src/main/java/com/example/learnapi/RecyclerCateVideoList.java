package com.example.learnapi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class RecyclerCateVideoList extends RecyclerView.Adapter<RecyclerCateVideoList.ViewHolder>{

    private static final String TAG = "RecyclerProfileListAdapter";

    private ArrayList<Integer> pkPost = new ArrayList<>();
    private ArrayList<String> namePost = new ArrayList<>();
    private ArrayList<String> authorPost = new ArrayList<>();
    private ArrayList<Integer> authorID = new ArrayList<>();
    private ArrayList<String> imgUrl = new ArrayList<>();
    private ArrayList<Integer> views = new ArrayList<>();
    private ArrayList<Integer> likes = new ArrayList<>();

    private Context mContext;

    public RecyclerCateVideoList(ArrayList<Integer> pkPost, ArrayList<String> namePost, ArrayList<String> authorPost,ArrayList<Integer> authorID,ArrayList<String> imgUrl,ArrayList<Integer> views, ArrayList<Integer> likes, Context mContext) {
        this.pkPost = pkPost;
        this.namePost = namePost;
        this.authorPost = authorPost;
        this.authorID = authorID;
        this.imgUrl = imgUrl;
        this.views = views;
        this.likes = likes;
        this.mContext = mContext;

    }

    public RecyclerCateVideoList(ArrayList<Integer> pkPost, ArrayList<String> namePost, ArrayList<String> authorPost, ArrayList<String> imgUrl, ArrayList<Integer> views, ArrayList<Integer> likes, FragmentActivity activity) {

    }

    @Override
    public RecyclerCateVideoList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        RecyclerCateVideoList.ViewHolder holder = new RecyclerCateVideoList.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerCateVideoList.ViewHolder holder, final int position) {

        holder.TViews.setText(String.valueOf(views.get(position)));
        holder.TName.setText(namePost.get(position));
        holder.textViewAuthor.setText(String.valueOf(authorPost.get(position)));
        Picasso.get()
                .load(String.valueOf(imgUrl.get(position)))
                .error(R.drawable.ic_podval_plus)
                .into(holder.thumbnail);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Bundle bundle = new Bundle();
                bundle.putInt("id",pkPost.get(position)); // Put anything what you want
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ShowPost();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();



            }
        });
    }

    @Override
    public int getItemCount() {
        return pkPost.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TViews;
        TextView TName;
        TextView textViewAuthor;
        ImageView thumbnail;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            TViews = itemView.findViewById(R.id.post_listitem_views);
            TName = itemView.findViewById(R.id.cate_listitem_name);
            textViewAuthor = itemView.findViewById(R.id.cate_postAuthor);
            thumbnail = itemView.findViewById(R.id.cate_item_thumbnail);


            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}