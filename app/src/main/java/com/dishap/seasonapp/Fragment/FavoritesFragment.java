package com.dishap.seasonapp.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dishap.seasonapp.Models.Recipe;
import com.dishap.seasonapp.R;
import com.dishap.seasonapp.RecyclerView.RecyclerViewAdapterFavorites;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FavoritesFragment extends Fragment {
    private List<Recipe> lstFavorites;
    private RecyclerView myrv;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        emptyView = RootView.findViewById(R.id.if_no_fav);
        myrv = RootView.findViewById(R.id.recyclerview_favorites);
        progressBar = RootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        getFavorites(RootView);
        return RootView;

    }

    private void getFavorites(final View rootView) {
        mAuth = FirebaseAuth.getInstance();
        final String id = mAuth.getCurrentUser().getUid();
        mRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Bookmarks");
        mRootRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstFavorites = new ArrayList<>();
                HashMap favorites = (HashMap) dataSnapshot.getValue();
                if (favorites != null) {
                    for (Object recipe : favorites.keySet()) {
                        String title = (String) dataSnapshot.child(recipe.toString()).child("title").getValue();
                        String img = (String) dataSnapshot.child(recipe.toString()).child("img").getValue();
                        lstFavorites.add(new Recipe(recipe.toString(), title, img, 0, 0));
                    }
                }
                else
                myrv = rootView.findViewById(R.id.recyclerview_favorites);
                if (lstFavorites.isEmpty()) {
                    myrv.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    RecyclerViewAdapterFavorites myAdapter = new RecyclerViewAdapterFavorites(getContext(), lstFavorites);
                    myrv.setAdapter(myAdapter);
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(delete);
                    itemTouchHelper.attachToRecyclerView(myrv);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String s = databaseError.toString();
                Toast.makeText(getContext(), "Error:" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    ItemTouchHelper.SimpleCallback delete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder deleteRecipe = new AlertDialog.Builder(getContext());
            deleteRecipe.setTitle("Delete the Recipe?");
            deleteRecipe.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    String recipeId = lstFavorites.get(position).getId();
                    lstFavorites.remove(position);
                    Objects.requireNonNull(myrv.getAdapter()).notifyItemRemoved(position);
                    deleteFromFirebase(recipeId);
                }
            });
            deleteRecipe.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Objects.requireNonNull(myrv.getAdapter()).notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                }
            });
            deleteRecipe.show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                    .addSwipeRightLabel("DELETE").addActionIcon(R.drawable.ic_delete)
                    .create().decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void deleteFromFirebase(String recipeId) {
        final String id = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Bookmarks");
        reference.child(recipeId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "The recipe has been deleted. :<", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Not able to delete the recipe.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}