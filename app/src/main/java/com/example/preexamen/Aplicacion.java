package com.example.preexamen;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import modelo.UsuariosDb;

public class Aplicacion extends Application {

    public static ArrayList<Usuario> usuarios;
    private AdapterUsuario usuarioAdapter;
    private UsuariosDb usuarioDb;

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    public AdapterUsuario getAdaptador() {
        return usuarioAdapter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        usuarioDb = new UsuariosDb(getApplicationContext());
        usuarios = usuarioDb.allUsuarios();
        usuarioDb.openDataBase();

        Log.d("", "onCreate: Tamaño array list: " + this.usuarios.size());
        this.usuarioAdapter = new AdapterUsuario(this.usuarios, this);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarioDb.openDataBase();
        long resultado = usuarioDb.insertUsuario(usuario);
        if (resultado != -1) {
            usuario.setId((int) resultado);
            usuarios.add(usuario);
            usuarioAdapter.notifyDataSetChanged();
        }
    }
}
