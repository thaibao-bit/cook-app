package com.example.learnapi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerCateList extends RecyclerView.Adapter<RecyclerCateList.ViewHolder> {
    private ArrayList<Integer> id = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<Integer> count = new ArrayList<>();
    Context context;

    public RecyclerCateList(ArrayList<Integer> id, ArrayList<String> category, ArrayList<Integer> count, Context context) {
        this.id = id;
        this.category = category;
        this.count = count;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        RecyclerCateList.ViewHolder holder = new  RecyclerCateList.ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(String.valueOf(category.get(position)));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category", category.get(position));
                bundle.putInt("id", id.get(position));
                Toast.makeText(context, "Count: "+ String.valueOf(count.get(position)), Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new CateVideo();
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
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.cate);
            relativeLayout = itemView.findViewById(R.id.cate_layout);
        }
    }
}
