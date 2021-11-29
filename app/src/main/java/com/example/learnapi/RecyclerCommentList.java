package com.example.learnapi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class RecyclerCommentList extends RecyclerView.Adapter<RecyclerCommentList.CommentViewHolder> {

    private static final String TAG = "RecyclerCommentListAdapter";

    private ArrayList<String> userID = new ArrayList<>();
    private ArrayList<String> content = new ArrayList<>();
    private ArrayList<Integer> authorID = new ArrayList<>();


    private Context mContext;

    public RecyclerCommentList(ArrayList<String> userID, ArrayList<String> content, ArrayList<Integer> authorID, Context mContext) {
        this.userID = userID;
        this.content = content;
        this.authorID = authorID;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.userID.setText(String.valueOf(userID.get(position)));
        holder.content.setText(content.get(position));
        holder.videoID.setText(String.valueOf(authorID.get(position)));// Video id but it's userID

        holder.userID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id",authorID.get(position));
                bundle.putString("bun_username", userID.get(position));
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
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView userID;
        TextView content;
        TextView videoID;
        RelativeLayout commentLayout;
      public CommentViewHolder(@NonNull View itemView) {
          super(itemView);
          userID = itemView.findViewById(R.id.comment_id_user);
          content = itemView.findViewById(R.id.comment_content);
          videoID = itemView.findViewById(R.id.comment_video);
          commentLayout = itemView.findViewById(R.id.comment_layout);
      }
  }
}