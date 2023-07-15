package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.preexamen.Usuario;

import java.util.ArrayList;

public class UsuariosDb implements Persistencia, Proyeccion {

    private UsuarioDbHelper dbHelper;
    private SQLiteDatabase db;

    public UsuariosDb(Context context) {
        dbHelper = new UsuarioDbHelper(context);
    }

    @Override
    public void openDataBase() {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getWritableDatabase();
        }
    }

    @Override
    public void closeDataBase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public long insertUsuario(Usuario usuario) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(DefineTabla.Usuarios.COLUMN_NOMBRE_USUARIO, usuario.getNombreUsuario());
        values.put(DefineTabla.Usuarios.COLUMN_CORREO, usuario.getCorreo());
        values.put(DefineTabla.Usuarios.COLUMN_CONTRASENA, usuario.getContrasena());
        long id = db.insert(DefineTabla.Usuarios.TABLE_NAME, null, values);
        closeDataBase();
        return id;
    }

    public long updateUsuario(Usuario usuario) {
        openDataBase();
        ContentValues values = new ContentValues();
        values.put(DefineTabla.Usuarios.COLUMN_NOMBRE_USUARIO, usuario.getNombreUsuario());
        values.put(DefineTabla.Usuarios.COLUMN_CORREO, usuario.getCorreo());
        values.put(DefineTabla.Usuarios.COLUMN_CONTRASENA, usuario.getContrasena());
        long rowsAffected = db.update(
                DefineTabla.Usuarios.TABLE_NAME,
                values,
                DefineTabla.Usuarios.COLUMN_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        closeDataBase();
        return rowsAffected;
    }

    public void deleteUsuario(int id) {
        openDataBase();
        db.delete(
                DefineTabla.Usuarios.TABLE_NAME,
                DefineTabla.Usuarios.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        closeDataBase();
    }


    @Override
    public Usuario getUsuario(String correo) {
        openDataBase();
        String[] projection = {
                DefineTabla.Usuarios.COLUMN_ID,
                DefineTabla.Usuarios.COLUMN_NOMBRE_USUARIO,
                DefineTabla.Usuarios.COLUMN_CORREO,
                DefineTabla.Usuarios.COLUMN_CONTRASENA
        };
        String selection = DefineTabla.Usuarios.COLUMN_CORREO + " = ?";
        String[] selectionArgs = {correo};
        Cursor cursor = db.query(
                DefineTabla.Usuarios.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            usuario = readUsuario(cursor);
        }
        cursor.close();
        return usuario;
    }


    @Override
    public ArrayList<Usuario> allUsuarios() {
        openDataBase();
        String[] projection = {
                DefineTabla.Usuarios.COLUMN_ID,
                DefineTabla.Usuarios.COLUMN_NOMBRE_USUARIO,
                DefineTabla.Usuarios.COLUMN_CORREO,
                DefineTabla.Usuarios.COLUMN_CONTRASENA
        };
        Cursor cursor = db.query(
                DefineTabla.Usuarios.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while (cursor.moveToNext()) {
            Usuario usuario = readUsuario(cursor);
            usuarios.add(usuario);
        }
        cursor.close();
        return usuarios;
    }

    @Override
    public Usuario readUsuario(Cursor cursor) {
        int idIndex = cursor.getColumnIndexOrThrow(DefineTabla.Usuarios.COLUMN_ID);
        int nombreUsuarioIndex = cursor.getColumnIndexOrThrow(DefineTabla.Usuarios.COLUMN_NOMBRE_USUARIO);
        int correoIndex = cursor.getColumnIndexOrThrow(DefineTabla.Usuarios.COLUMN_CORREO);
        int contrasenaIndex = cursor.getColumnIndexOrThrow(DefineTabla.Usuarios.COLUMN_CONTRASENA);

        int id = cursor.getInt(idIndex);
        String nombreUsuario = cursor.getString(nombreUsuarioIndex);
        String correo = cursor.getString(correoIndex);
        String contrasena = cursor.getString(contrasenaIndex);

        return new Usuario(id, nombreUsuario, correo, contrasena);
    }
}
