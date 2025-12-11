package com.example.oriontek.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.oriontek.Helpers.UtilsHelper;
import com.example.oriontek.Models.Client;
import com.example.oriontek.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AddressActivity extends AppCompatActivity {

    private ImageView imgPhoto;
    private TextView name, address1, address2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setToolbar();
        onInit();

        // Obtener ID del intent y validar
        final String mIdClient = getIntent().getStringExtra("value");

        if (mIdClient == null) {
            finish(); // Evitar crash si no llega el parámetro
            return;
        }

        // Obtener datos del cliente
        UtilsHelper.getDatabase()
                .child("client")
                .child(mIdClient)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        onSetProduct(snapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void onSetProduct(DataSnapshot snapshot) {
        Client client = snapshot.getValue(Client.class);

        if (client == null) return;

        Glide.with(AddressActivity.this)
                .load(client.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(imgPhoto);

        name.setText(client.getName());
        address1.setText(client.getAddress1());
        address2.setText(client.getAddress2());
    }

    // Configurar toolbar
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detail_title_message);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    // Referenciar vistas
    private void onInit() {
        imgPhoto = findViewById(R.id.imgPhoto);
        name = findViewById(R.id.tvName);
        address1 = findViewById(R.id.tvAddress1);
        address2 = findViewById(R.id.tvAddress2);
    }
}
