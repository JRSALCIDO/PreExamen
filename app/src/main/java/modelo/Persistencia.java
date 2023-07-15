package modelo;

import com.example.preexamen.Usuario;

public interface Persistencia {
    public void openDataBase();
    public void closeDataBase();
    public long insertUsuario(Usuario usuario);
}

