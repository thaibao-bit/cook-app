package com.example.learnapi;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnapi.Model.Comment;
import com.example.learnapi.Model.LikeModel;
import com.example.learnapi.Model.PostModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowPost extends Fragment implements View.OnClickListener {

    TextView v_sh_title;
    TextView v_sh_text;
    TextView v_sh_like, show_ingre, show_step;
    Button button_like;
    VideoView videoView;
    ProgressDialog progressDialog;
    private ArrayList<String> userID = new ArrayList<>();
    private ArrayList<String> content = new ArrayList<>();
    private ArrayList<Integer> videoID = new ArrayList<>(); // Video id but it's userID
    RecyclerView recyclerComment;
    EditText edtComment;
    Button btnComment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.show_post, container, false);

        v_sh_title = rootView.findViewById(R.id.vshow_title);
        v_sh_text = rootView.findViewById(R.id.vshow_text);
        v_sh_like = rootView.findViewById(R.id.vshow_like);
        show_ingre = rootView.findViewById(R.id.show_ingre);
        show_step = rootView.findViewById(R.id.show_step);
        videoView = rootView.findViewById(R.id.videoView);
        recyclerComment = rootView.findViewById(R.id.recycler_comment);
        edtComment = rootView.findViewById(R.id.edtComment);
        btnComment = rootView.findViewById(R.id.comment_button);
        button_like = rootView.findViewById(R.id.button_like);


        button_like.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ClearList();
            Integer bundle_id = bundle.getInt("id");
            Toast.makeText(getContext(), bundle_id.toString(), Toast.LENGTH_SHORT).show();
            GetServerData(bundle_id);


        }


        getActivity().setTitle("Show Post");


        return rootView;

    }

    private void ClearList() {
        userID.clear();
        content.clear();
        videoID.clear();

        RecyclerCommentList adapter = new RecyclerCommentList(userID, content, videoID, getActivity());
        adapter.notifyDataSetChanged();
        recyclerComment.setAdapter(adapter);
    }

    public void LikeToggle(Integer id){
        String str_id = String.valueOf(id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());
        PostApi postApi = retrofit.create(PostApi.class);
        Call<LikeModel> likeModelCall = postApi.getLikeToggle(queryToken_ap,str_id);
        likeModelCall.enqueue(new Callback<LikeModel>() {
            @Override
            public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {
                if (response.isSuccessful())
                {
                    if (response.body() != null){
                        LikeModel likeModel = response.body();
                        String like_num = "Like: " + String.valueOf(likeModel.getCount());
                        v_sh_like.setText(like_num);
                    }
                }
                else {
                    Toast.makeText(getContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LikeModel> call, Throwable t) {
                Toast.makeText(getContext(), "like failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddPostServer(Comment comment) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());
        PostApi postApi = retrofit.create(PostApi.class);
        Call<Comment> call = postApi.postComment(queryToken_ap, comment);

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                progressDialog.dismiss();
                Log.d("good", "good");
                Toast.makeText(getContext(), "Posted and response " +response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("fail", "fail");
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetServerData(Integer getted_id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String data = String.valueOf(getted_id);

        PostApi postApi = retrofit.create(PostApi.class);

        Call<PostModel> call = postApi.getPost(data);

        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        PostModel postValues = response.body();

                        String v_sh_str_title = postValues.getTitle();
                        String v_sh_str_text = postValues.getDescription();
                        Integer v_sh_str_like = postValues.getLikecount();
                        String string_like = "Like: " +v_sh_str_like;
                        String url = postValues.getVideo();
                        String ingre = postValues.getIngredients();
                        String step = postValues.getDirections();

                        v_sh_title.setText(v_sh_str_title);
                        v_sh_text.setText(v_sh_str_text);
                        v_sh_like.setText(string_like);
//                        v_sh_like.setText(String.valueOf(v_sh_str_like));
                        show_ingre.setText(ingre);
                        show_step.setText(step);
                        videoView.setVideoURI(Uri.parse(url));
                        videoView.seekTo(1);
                        videoView.start();
                        MediaController controller = new MediaController(getContext());
                        videoView.setMediaController(controller);
                        controller.setAnchorView(videoView);

                    }

                } else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });

        Call<List<Comment>> commentCall = postApi.getCommentList(data);
        commentCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Comment> commentList = response.body();
                        for (Comment h : commentList) {
                            userID.add(h.getUsername());
                            content.add(h.getComment());
                            videoID.add(h.getUser());
                        }
                        initRecyclerview();
                    }

                }
            }

            private void initRecyclerview() {
                Log.d("Home", "initRecyclerView: init recyclerview.");
                RecyclerCommentList adapter = new RecyclerCommentList(userID, content, videoID, getActivity());
                recyclerComment.setAdapter(adapter);
                recyclerComment.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        Bundle bundle = this.getArguments();
        switch (v.getId()) {
            case R.id.comment_button:
                progressDialog.setTitle("Posting...");
                progressDialog.show();
                PostComment();

                if (bundle != null) {
                    ClearList();
                    Integer bundle_id = bundle.getInt("id");
                    Toast.makeText(getContext(), bundle_id.toString(), Toast.LENGTH_SHORT).show();
                    GetServerData(bundle_id);
                }
                break;
            case R.id.button_like:

                if (bundle != null) {

                    Integer bundle_id = bundle.getInt("id");
                    LikeToggle(bundle_id);
                }

                break;
        }

        }


    private void PostComment() {
        if (!IsEmptyEditTextLogin()) {
            if (InternetUtil.isInternetOnline(getActivity())) {
                Post();
            }
        }
    }

    private boolean IsEmptyEditTextLogin() {
        if (edtComment.getText().toString().isEmpty()) {

            Toast toast = Toast.makeText(getActivity(), "Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return true;
        } else {
            return false;
        }
    }

    private void Post() {
        String cmt = edtComment.getText().toString();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Integer bundle_id = bundle.getInt("id");
            int vid = bundle_id;


            Comment comment = new Comment(
                    cmt, vid
            );


            if (InternetUtil.isInternetOnline(getActivity())) {
                AddPostServer(comment);
            }
        }

    }
    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}

