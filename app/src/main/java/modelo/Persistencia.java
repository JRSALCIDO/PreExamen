package modelo;

import com.example.preexamen.Usuario;

public interface Persistencia {
    void openDataBase();
    void closeDataBase();
    long insertUsuario(Usuario usuario);
    long updateUsuario(Usuario usuario);
    void deleteUsuario(int id);
}

