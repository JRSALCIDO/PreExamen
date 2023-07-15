package modelo;

import android.database.Cursor;

import com.example.preexamen.Usuario;

import java.util.ArrayList;

public interface Proyeccion {
    Usuario getUsuario(String correo);
    ArrayList<Usuario> allUsuarios();
    Usuario readUsuario(Cursor cursor);
}
