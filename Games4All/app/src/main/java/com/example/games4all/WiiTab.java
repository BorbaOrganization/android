package com.example.games4all;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WiiTab extends Fragment {

    List<Game> lstGame;

    protected  static boolean internalCall = false;
    private RecycleViewAdapter mrRecycleViewAdapter;
    private FirebaseDatabase database;
    private DatabaseReference tableGame;

    public WiiTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wii_tab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        tableGame = database.getInstance().getReference("game");
        internalCall = true;

        lstGame=new ArrayList<>();
        //  lstGame.add(new Game("Assasin Creed","pg13","Strategy","PS3","Description Game","https://upload.wikimedia.org/wikipedia/en/thumb/5/52/Assassin%27s_Creed.jpg/220px-Assassin%27s_Creed.jpg"));


        mrRecycleViewAdapter=new RecycleViewAdapter(getContext(),lstGame);
        search();


        RecyclerView myrv=(RecyclerView) view.findViewById(R.id.recyclerviewWii_id);

        myrv.setLayoutManager(new GridLayoutManager(getContext(),2));
        myrv.setAdapter(mrRecycleViewAdapter);
    }

    private void search() {

        tableGame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(internalCall==false){
                    return;}
                else {
                    for (DataSnapshot gameSnapshot : dataSnapshot.getChildren()) {
                        Game game = gameSnapshot.getValue(Game.class);
                        if (game.getConsole().equals("Wii")) {
                            lstGame.add(game);
                        }
                    }
                    mrRecycleViewAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
