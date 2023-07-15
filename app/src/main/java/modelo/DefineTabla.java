package modelo;

public class DefineTabla {

    public static abstract class Usuarios {
        public static final String TABLE_NAME = "usuarios";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRE_USUARIO = "nombreUsuario";
        public static final String COLUMN_CORREO = "correo";
        public static final String COLUMN_CONTRASENA = "contrasena";
    }
}
