package com.example.learnapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnapi.Model.PostModel;
import com.example.learnapi.Model.ProfileModel;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyProfile extends Fragment {


    private ArrayList<Integer> pkPost = new ArrayList<>();
    private ArrayList<String> namePost = new ArrayList<>();
    private ArrayList<String> authorPost = new ArrayList<>();
    private ArrayList<Integer> authorID = new ArrayList<>();
    private ArrayList<String> imgPost = new ArrayList<>();
    private ArrayList<Integer> views = new ArrayList<>();
    private ArrayList<Integer> likes = new ArrayList<>();
    private RecyclerView recyclerView;
    TextView txtUserName, txtEmail, txtName;
    ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_profile, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_profile_list);
        txtUserName = rootView.findViewById(R.id.txt_username);
        txtEmail = rootView.findViewById(R.id.txt_email);
        txtName = rootView.findViewById(R.id.txt_real_name);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        txtUserName.setText("alo");

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            ClearList();
            showAllPosts();
            showInfo();
        }


        getActivity().setTitle("My Profile");

        return rootView;


    }
    private void showInfo()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());
        PostApi postApi= retrofit.create(PostApi.class);

        Call<List<ProfileModel>> call = postApi.getMyProfile(queryToken_ap);
        call.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        List<ProfileModel> model1 = response.body();
                        for (ProfileModel model: model1) {
                            String name = model.getFirst_name();
                            String mail = model.getEmail();
                            String uname = model.getUsername();
                            txtName.setText(name);
                            txtEmail.setText(mail);
                            txtUserName.setText(uname);
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(), "Failed get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                progressDialog.dismiss();
                txtName.setText("Hello World");
                txtEmail.setText("Hello World");
                txtUserName.setText(t.getMessage());
                Toast.makeText(getContext(), "Failed connect data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAllPosts() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());
        PostApi postApi= retrofit.create(PostApi.class);

        Call<List<PostModel>> call = postApi.getMyVideo(queryToken_ap);

        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                progressDialog.dismiss();

                if(response.isSuccessful()){

                    if (response.body() != null) {
                        List<PostModel> postList = response.body();

                        for(PostModel h:postList){


                            Integer au_id = h.getAuthor();
                            authorID.add(au_id);

                            Integer cat_id = h.getId();
                            pkPost.add(cat_id);

                            String cat_name = h.getTitle();
                            namePost.add(cat_name);

                            String cat_author = h.getUsername();
                            authorPost.add(cat_author);

                            String cat_img = h.getImage();
                            imgPost.add(cat_img);

                            Integer cat_view = h.getViews();
                            views.add(cat_view);

                            Integer cat_like = h.getLikecount();
                            views.add(cat_like);



                        }

                        initRecyclerView();

                    }

                }else {
                    Log.d("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }

        });

    }


    private void initRecyclerView(){
        Log.d("Home", "initRecyclerView: init recyclerview.");
        RecyclerHomeList adapter = new RecyclerHomeList(pkPost,  namePost , authorPost,authorID, imgPost,views,likes, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void ClearList()
    {
        pkPost.clear();
        namePost.clear();

        RecyclerHomeList adapter = new RecyclerHomeList(pkPost,  namePost , authorPost, authorID, imgPost,views,likes, getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }





}