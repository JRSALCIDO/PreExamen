package com.example.preexamen;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etContraseña, etConfirmarContraseña;
    private Button btnRegistrarse, btnRegresar;

    private UsuariosDb usuariosDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContraseña = findViewById(R.id.etContraseña);
        etConfirmarContraseña = findViewById(R.id.etConfirmarContraseña);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnRegresar = findViewById(R.id.btnRegresar);

        UsuarioDbHelper usuarioDbHelper = new UsuarioDbHelper(this);
        UsuariosDb usuariosDb = new UsuariosDb(this);



        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String nombre = etNombre.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String contraseña = etContraseña.getText().toString();
                String confirmarContraseña = etConfirmarContraseña.getText().toString();

                // Validar que los campos no estén vacíos
                if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar que las contraseñas coincidan
                if (!contraseña.equals(confirmarContraseña)) {
                    Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear un objeto Usuario con los valores proporcionados
                Usuario usuario = new Usuario(nombre, correo, contraseña);

                // Insertar el usuario en la base de datos
                long resultado = usuariosDb.insertUsuario(usuario);

                if (resultado != -1) {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistroActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

