package com.example.preexamen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import modelo.UsuariosDb;

public class ListaActivity extends AppCompatActivity implements AdapterUsuario.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnRegresar;
    private Aplicacion app;
    private Usuario usuario;
    private int posicion;
    private UsuariosDb usuarioDb;
    private AdapterUsuario adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        inicializarComponentes();
        setupRecyclerView();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegresar();
            }
        });

        adaptador.setOnItemClickListener(this);
    }

    private void inicializarComponentes() {
        usuario = new Usuario();
        posicion = -1;
        app = (Aplicacion) getApplication();
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.listaUsuarios);
        adaptador = new AdapterUsuario(app.getUsuarios(), this);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(layoutManager);

        btnRegresar = findViewById(R.id.btnRegresar);
        usuarioDb = new UsuariosDb(getApplicationContext());
    }

    private void setupRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void btnRegresar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de que quieres regresar?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Regresar al MainActivity
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        posicion = position;
        usuario = app.getUsuarios().get(posicion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            app.getUsuarios().clear();
            app.getUsuarios().addAll(usuarioDb.allUsuarios());
            adaptador.setUsuarioList(app.getUsuarios());
            recreate();
            adaptador.setUsuarioList(app.getUsuarios());
        }

        posicion = -1;
        usuario = null;
    }
}


