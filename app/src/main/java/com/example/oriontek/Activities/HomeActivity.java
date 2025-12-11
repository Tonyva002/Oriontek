package com.example.oriontek.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oriontek.Adapters.ClientAdapter;
import com.example.oriontek.Helpers.UtilsHelper;
import com.example.oriontek.Models.Client;
import com.example.oriontek.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ClientAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


            onInit();
            setToolbar();


            UtilsHelper.getDatabase().child("client").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final ArrayList<Client> clients = new ArrayList<>();

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Client post = dataSnapshot.getValue(Client.class);
                        assert post != null;
                        post.setIdClient(dataSnapshot.getKey());
                        clients.add(post);
                    }
                    mAdapter = new ClientAdapter(HomeActivity.this, clients, R.layout.client_adapter, (client, position) -> goToAddress(clients.get(position).getIdClient()));

                    mLinearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setHasFixedSize(true);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
    }


    // Metodo para referenciar las vistas del layout
    private void onInit(){
        mRecyclerView = findViewById(R.id.recyclerview);
    }


    // Metodo para configurar la toolbar
    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }
    // Metodo para navegar a  AddressActivity
    private void goToAddress(String key){
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra("value", key);
        startActivity(intent);
    }
}