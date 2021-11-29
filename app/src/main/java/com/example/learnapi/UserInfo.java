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


public class UserInfo extends Fragment {


    private ArrayList<Integer> pkPost = new ArrayList<>();
    private ArrayList<String> namePost = new ArrayList<>();
    private ArrayList<String> authorPost = new ArrayList<>();
    private ArrayList<Integer> authorID = new ArrayList<>();
    private ArrayList<String> imgPost = new ArrayList<>();
    private RecyclerView recyclerView;
    TextView txtUserName, txtEmail, txtName;
    ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_info, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_author_video_list);
        txtUserName = rootView.findViewById(R.id.txt_author);
        txtEmail = rootView.findViewById(R.id.txt_author_email);
        txtName = rootView.findViewById(R.id.txt_author_real_name);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        txtUserName.setText("alo");


        if ( InternetUtil.isInternetOnline(getActivity()) ){

            ClearList();
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                ClearList();
                String bundler_user = bundle.getString("bun_username");
                getActivity().setTitle(bundler_user +" Profile");
                Integer bundle_id = bundle.getInt("user_id");
                Toast.makeText(getContext(), bundle_id.toString(), Toast.LENGTH_SHORT).show();
                showAllPosts(bundle_id);
                showInfo(bundle_id);


            }



        }


        return rootView;


    }
    private void showInfo(Integer id_num)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String idnum = String.valueOf(id_num);
        PostApi postApi= retrofit.create(PostApi.class);

        Call<ProfileModel> call = postApi.getUser(idnum);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        ProfileModel model = response.body();

                            String name = model.getFirst_name();
                            String mail = model.getEmail();
                            String uname = model.getUsername();
                            txtName.setText(name);
                            txtEmail.setText(mail);
                            txtUserName.setText(uname);

                    }
                }
                else {
                    Toast.makeText(getContext(), "Failed get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                progressDialog.dismiss();
                txtName.setText("Hello World");
                txtEmail.setText("Hello World");
                txtUserName.setText(t.getMessage());
                Toast.makeText(getContext(), "Failed connect data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAllPosts(Integer id_num ) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi= retrofit.create(PostApi.class);

        String idnum = String.valueOf(id_num);
        Call<List<PostModel>> call = postApi.getUserVideo(idnum);

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

                            String cat_name = h.getCaption();
                            namePost.add(cat_name);

                            String cat_author = h.getUsername();
                            authorPost.add(cat_author);

                            String cat_img = h.getImage();
                            imgPost.add(cat_img);


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
        RecyclerHomeList adapter = new RecyclerHomeList(pkPost,  namePost , authorPost,authorID, imgPost, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void ClearList()
    {
        pkPost.clear();
        namePost.clear();

        RecyclerHomeList adapter = new RecyclerHomeList(pkPost,  namePost , authorPost,authorID,imgPost, getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }





}