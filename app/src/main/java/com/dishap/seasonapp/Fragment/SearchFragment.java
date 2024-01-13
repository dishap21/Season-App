package com.dishap.seasonapp.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;


import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dishap.seasonapp.Activities.RecipeActivity;
import com.dishap.seasonapp.Models.Recipe;
import com.dishap.seasonapp.R;
import com.dishap.seasonapp.RecyclerView.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private RecyclerView myrv;
    private ProgressBar progressBar;
    private SearchView searchView;
    private List<Recipe> searchRecipe;
    private HorizontalScrollView scrollView;
    private JSONArray testArr;
    private Button fingerfood, salad, soup, beverage, desserts, appetizer;
    public String api_key = "your_api_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = RootView.findViewById(R.id.search_sv);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public boolean onQueryTextSubmit(String s) {
                final String recipeQuery = s.trim();
                if(recipeQuery.isEmpty()){
                    Toast.makeText(getActivity(), "We have not got the text.", Toast.LENGTH_LONG).show();
                }else{
                    searchRecipe(s);
                    progressBar = RootView.findViewById(R.id.progressbar3);
                    progressBar.setVisibility(View.VISIBLE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        scrollView = RootView.findViewById(R.id.scrollView2);
        fingerfood = RootView.findViewById(R.id.fingerfood);
        salad = RootView.findViewById(R.id.salad);
        soup = RootView.findViewById(R.id.soup);
        beverage = RootView.findViewById(R.id.beverage);
        desserts = RootView.findViewById(R.id.desserts);
        appetizer = RootView.findViewById(R.id.appetizer);

        fingerfood.setOnClickListener(this);
        salad.setOnClickListener(this);
        soup.setOnClickListener(this);
        beverage.setOnClickListener(this);
        desserts.setOnClickListener(this);
        appetizer.setOnClickListener(this);

        myrv = RootView.findViewById(R.id.search_recyclerview2);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        return RootView;
    }

    private void searchRecipe(String search) {
        searchRecipe = new ArrayList<Recipe>();
        String URL="https://api.spoonacular.com/recipes/search?query=" + search +
                "&number=30&instructionsRequired=true&apiKey="+ api_key;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        testArr = (JSONArray) response.get("results");
                        Log.i("the search res is:", String.valueOf(testArr));
                        for (int i = 0; i < testArr.length(); i++) {
                            JSONObject jsonObject1;
                            jsonObject1 = testArr.getJSONObject(i);
                            searchRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"),
                                    "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"),
                                    Integer.parseInt(jsonObject1.optString("servings")),
                                    Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                        }
                        if(searchRecipe.isEmpty()){
                            myrv.setAlpha(0);
                            Toast.makeText(getActivity(), "No results available for " + search, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            scrollView.setVisibility(View.VISIBLE);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
                            myrv.setAdapter(myAdapter);
                            myrv.setAlpha(1);
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View view) {
        if(view==fingerfood){
            searchRecipe("fingerfood");
        }
        else if(view==desserts){
            searchRecipe("desserts");
        }
        else if(view==soup){
            searchRecipe("soup");
        }
        else if(view==salad){
            searchRecipe("salad");
        }
        else if(view==appetizer){
            searchRecipe("appetizer");
        }
        else if(view==beverage){
            searchRecipe("beverage");
        }
    }
}