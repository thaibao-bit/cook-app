package com.example.learnapi;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class Footer extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.footer, container, false);

        LinearLayout lbuttonHome = (LinearLayout) v.findViewById(R.id.lbuttonHome);
        LinearLayout lbuttonCategory = (LinearLayout) v.findViewById(R.id.lbuttonCategory);
        LinearLayout lbuttonPlus = (LinearLayout) v.findViewById(R.id.lbuttonPlus);
        LinearLayout lbuttonProfile = (LinearLayout) v.findViewById(R.id.lbuttonProfile);
        LinearLayout lbuttonLogin = (LinearLayout) v.findViewById(R.id.lbuttonLogin);

        lbuttonHome.setOnClickListener(this);
        lbuttonCategory.setOnClickListener(this);
        lbuttonPlus.setOnClickListener(this);
        lbuttonProfile.setOnClickListener(this);
        lbuttonLogin.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.lbuttonHome:
                fragment = new Home();
                replaceFragment(fragment);
                break;
            case R.id.lbuttonLogin:
//                if (CheckLogin()){
//                    fragment = new Home();
//                    Toast.makeText(getContext(), "You has been logged in", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    fragment = new ProfileLogin();
//                }
                fragment = new ProfileLogin();
                replaceFragment(fragment);
                break;

            case R.id.lbuttonCategory:
                fragment = new Category();
                replaceFragment(fragment);
                break;
//
//            case R.id.lbuttonPlus:
//                runtimePermission();
//                if (CheckLogin()) {
//                    fragment = new PostAdd();
//                }else {
//                    fragment = new ProfileLogin();
//                }
//                replaceFragment(fragment);
//                break;
//
            case R.id.lbuttonProfile:

                if (CheckLogin()) {
                    fragment = new MyProfile();
                }else {
                    fragment = new ProfileLogin();
                }
                replaceFragment(fragment);
                break;


        }
    }

    public void runtimePermission()
    {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        openGallery();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

    }


    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public Boolean CheckLogin() {

        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean shered_category_id = preferences.getBoolean("loggedin",  false);

        if (shered_category_id) {
            return true;
        }
        else {
            return false;
        }
    }







}
