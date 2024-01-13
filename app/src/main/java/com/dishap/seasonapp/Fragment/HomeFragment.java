package com.dishap.seasonapp.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dishap.seasonapp.Activities.LoginActivity;
import com.dishap.seasonapp.Activities.ProfileActivity;
import com.dishap.seasonapp.Models.Recipe;
import com.dishap.seasonapp.R;
import com.dishap.seasonapp.RecyclerView.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Recipe> listRecipe = new ArrayList<>();
    private JSONArray testArr;
    private RecyclerView myrv;
    private ImageButton profile;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView emptyView, hiUsertv, customApp;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    public String api_key = "your_api_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        profile = RootView.findViewById(R.id.profilePic);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        progressBar = RootView.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        emptyView= RootView.findViewById(R.id.empty_view2);
        hiUsertv = RootView.findViewById(R.id.hiUserr);
        customApp = RootView.findViewById(R.id.appNameCustom);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/mrs-sheppards.regular.ttf");
        customApp.setTypeface(customFont);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            showUserName();
        }
        else{
            sendToLogin();
        }
        myrv = RootView.findViewById(R.id.recyclerview);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getRandomRecipes();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        return RootView;
    }

    private void showUserName() {
        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.child(id).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String hiUser = task.getResult().getValue(String.class);
                hiUsertv.setText("Hi!! " + hiUser);
            }
        });
    }

    private void sendToLogin() {
        Intent sendToLogin = new Intent(getActivity(), LoginActivity.class);
        startActivity(sendToLogin);
        requireActivity().finish();
    }

    private void getRandomRecipes() {
        String URL = " https://api.spoonacular.com/recipes/random?number=40&apiKey="+api_key;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("recipes");
                            Log.i("the res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                listRecipe.add(new Recipe(jsonObject1.optString("id"),
                                        jsonObject1.optString("title"), jsonObject1.optString("image"),
                                        Integer.parseInt(jsonObject1.optString("servings")),
                                        Integer.parseInt(jsonObject1.optString("readyInMinutes" ))));
                            }
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), listRecipe);
                            myrv.setAdapter(myAdapter);
                            progressBar.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                        //progressBar.setVisibility(View.GONE);
                        myrv.setAlpha(0);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}