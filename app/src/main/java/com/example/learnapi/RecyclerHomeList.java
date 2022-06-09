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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class RecyclerHomeList extends RecyclerView.Adapter<RecyclerHomeList.ViewHolder>{

    private static final String TAG = "RecyclerProfileListAdapter";


    private ArrayList<Integer> pkPost = new ArrayList<>();
    private ArrayList<String> namePost = new ArrayList<>();
    private ArrayList<String> authorPost = new ArrayList<>();
    private ArrayList<Integer> authorID = new ArrayList<>();
    private ArrayList<String> imgUrl = new ArrayList<>();
    private ArrayList<Integer> views = new ArrayList<>();
    private ArrayList<Integer> likes = new ArrayList<>();
    private Context mContext;

    public RecyclerHomeList(ArrayList<Integer> pkPost, ArrayList<String> namePost, ArrayList<String> authorPost, ArrayList<Integer> authorID, ArrayList<String> imgUrl,ArrayList<Integer> views, ArrayList<Integer> likes, Context mContext) {
        this.pkPost = pkPost;
        this.namePost = namePost;
        this.authorPost = authorPost;
        this.authorID = authorID;
        this.imgUrl = imgUrl;
        this.views = views;
        this.likes = likes;
        this.mContext = mContext;
    }

    @Override
    public RecyclerHomeList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        RecyclerHomeList.ViewHolder holder = new RecyclerHomeList.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerHomeList.ViewHolder holder, final int position) {

        holder.TViews.setText(String.valueOf(views.get(position)));
        holder.TName.setText(String.valueOf(namePost.get(position)));
        holder.textViewAuthor.setText(String.valueOf(authorPost.get(position)));
        Picasso.get()
                .load(String.valueOf(imgUrl.get(position)))
                .error(R.drawable.ic_podval_plus)
                .into(holder.thumbnail);
        holder.textViewAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("user_id",authorID.get(position));
                bundle.putString("bun_username", authorPost.get(position));// Put anything what you want
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new UserInfo();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

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
            TName = itemView.findViewById(R.id.post_listitem_name);
            textViewAuthor = itemView.findViewById(R.id.postAuthor);
            thumbnail = itemView.findViewById(R.id.thumbnail);


            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}