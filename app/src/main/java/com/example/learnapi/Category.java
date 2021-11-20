package com.example.learnapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnapi.Model.CateModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Category extends Fragment {
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<Integer> count = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    private ArrayList<Integer> id = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category, container, false);
        recyclerView = view.findViewById(R.id.recycler_category_list);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            ClearList();
            showAllCates();
        }


        getActivity().setTitle("Categories");
        
        return view;
    }

    private void showAllCates() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi = retrofit.create(PostApi.class);
        Call<List<CateModel>> listCall = postApi.getCateList();

        listCall.enqueue(new Callback<List<CateModel>>() {
            @Override
            public void onResponse(Call<List<CateModel>> call, Response<List<CateModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        List<CateModel> categoryList = response.body();
                        for (CateModel c:categoryList)
                        {
                            Integer idNum = c.getId();
                            String cate = c.getCategory();
                            Integer dem = c.getCount();

                            categories.add(cate);
                            id.add(idNum);
                            count.add(dem);

                        }
                        initRecyclerView();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Failed getting data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CateModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });
    }

    private void initRecyclerView() {
        RecyclerCateList adapter = new RecyclerCateList(id,categories, count,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void ClearList() {
        categories.clear();

        RecyclerCateList adapter = new RecyclerCateList(id,categories,count, getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
