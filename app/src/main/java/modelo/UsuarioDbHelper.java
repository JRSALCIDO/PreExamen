package modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user2.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_USUARIO =
            "CREATE TABLE " +
                    DefineTabla.Usuarios.TABLE_NAME + " (" +
                    DefineTabla.Usuarios.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DefineTabla.Usuarios.COLUMN_NOMBRE_USUARIO + " TEXT, " +
                    DefineTabla.Usuarios.COLUMN_CORREO + " TEXT UNIQUE, " +
                    DefineTabla.Usuarios.COLUMN_CONTRASENA + " TEXT)";

    private static final String SQL_DELETE_USUARIO =
            "DROP TABLE IF EXISTS " + DefineTabla.Usuarios.TABLE_NAME;

    public UsuarioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USUARIO);
        onCreate(db);
    }
}




