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
    private ArrayList<Integer> authorPost = new ArrayList<>();
    private ArrayList<String> imgUrl = new ArrayList<>();

    private Context mContext;

    public RecyclerHomeList(ArrayList<Integer> pkPost, ArrayList<String> namePost, ArrayList<Integer> authorPost,ArrayList<String> imgUrl, Context mContext) {
        this.pkPost = pkPost;
        this.namePost = namePost;
        this.authorPost = authorPost;
        this.imgUrl = imgUrl;
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

        holder.TId.setText(String.valueOf(pkPost.get(position)));
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

        TextView TId;
        TextView TName;
        TextView textViewAuthor;
        ImageView thumbnail;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            TId = itemView.findViewById(R.id.post_listitem_id);
            TName = itemView.findViewById(R.id.post_listitem_name);
            textViewAuthor = itemView.findViewById(R.id.postAuthor);
            thumbnail = itemView.findViewById(R.id.thumbnail);


            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}