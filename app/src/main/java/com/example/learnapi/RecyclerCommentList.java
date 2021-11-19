package com.example.learnapi;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class RecyclerCommentList extends RecyclerView.Adapter<RecyclerCommentList.CommentViewHolder> {

    private static final String TAG = "RecyclerCommentListAdapter";

    private ArrayList<Integer> userID = new ArrayList<>();
    private ArrayList<String> content = new ArrayList<>();
    private ArrayList<Integer> videoID = new ArrayList<>();


    private Context mContext;

    public RecyclerCommentList(ArrayList<Integer> userID, ArrayList<String> content, ArrayList<Integer> videoID, Context mContext) {
        this.userID = userID;
        this.content = content;
        this.videoID = videoID;
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
        holder.videoID.setText(String.valueOf(videoID.get(position)));
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