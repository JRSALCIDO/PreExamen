package com.example.preexamen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import modelo.UsuariosDb;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnExit;

    private boolean isLoggedIn = false;
    private static UsuariosDb usuariosDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los elementos de la interfaz
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnExit = findViewById(R.id.btnExit);

        // Obtener instancia de la base de datos
        usuariosDb = new UsuariosDb(this);

        // Abrir la base de datos
        usuariosDb.openDataBase();

        // Configurar el listener del botón "Ingresar"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al hacer clic en el botón "Ingresar"
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Aquí puedes implementar la lógica para validar las credenciales de inicio de sesión
                if (validarCredenciales(email, password)) {
                    // Credenciales válidas, mostrar mensaje de éxito
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    isLoggedIn = true;
                    actualizarInterfaz();
                } else {
                    // Credenciales inválidas, mostrar mensaje de error
                    Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el listener del botón "Registrarse"
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al hacer clic en el botón "Registrarse"
                // Aquí puedes implementar la lógica para abrir la pantalla de registro
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el listener del botón "Salir"
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmar");
                builder.setMessage("¿Estás seguro de que quieres salir?");
                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción al confirmar salir
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción al cancelar
                    }
                });
                builder.show();
            }
        });

        // Cerrar la base de datos
        usuariosDb.closeDataBase();

        // Actualizar la interfaz según el estado de inicio de sesión
        actualizarInterfaz();
    }

    private boolean validarCredenciales(String email, String password) {
        Log.d("MainActivity", "Correo electrónico: " + email);
        Log.d("MainActivity", "Contraseña: " + password);
        // Obtener instancia de la base de datos
        UsuariosDb usuariosDb = new UsuariosDb(this);

        // Abrir la base de datos
        usuariosDb.openDataBase();

        // Realizar la consulta para verificar si las credenciales son válidas
        Usuario usuario = usuariosDb.getUsuario(email);

        // Verificar si se encontró un usuario con el email dado y si la contraseña coincide
        if (usuario != null && usuario.getContrasena().equals(password)) {
            // Credenciales válidas
            usuariosDb.closeDataBase();
            return true;
        } else {
            // Credenciales inválidas
            usuariosDb.closeDataBase();
            return false;
        }
    }




    private void actualizarInterfaz() {
        if (isLoggedIn) {
            // Si el usuario ha iniciado sesión, ocultar los elementos del formulario de inicio de sesión
            etEmail.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
        } else {
            // Si el usuario no ha iniciado sesión, mostrar los elementos del formulario de inicio de sesión
            etEmail.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
        }
    }
}
