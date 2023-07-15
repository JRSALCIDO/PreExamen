package com.example.preexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import modelo.UsuariosDb;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtNombreUsuario, txtCorreo, txtContrasena, txtReContrasena;
    private Button btnRegistrar, btnIngresar;
    private UsuariosDb usuarioDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegistrar();
            }
        });
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIngresar();
            }
        });
    }

    private void inicializarComponentes() {
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.etContraseña);
        txtReContrasena = findViewById(R.id.etConfirmarContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrarse);
        btnIngresar = findViewById(R.id.btnRegresar);
        usuarioDb = new UsuariosDb(getApplicationContext());
    }

    private void btnRegistrar() {
        String nombreUsuario = txtNombreUsuario.getText().toString();
        String correo = txtCorreo.getText().toString();
        String contrasena = txtContrasena.getText().toString();
        String reContrasena = txtReContrasena.getText().toString();

        if (nombreUsuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || reContrasena.isEmpty()) {
            Toast.makeText(RegistroActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(reContrasena)) {
            Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(RegistroActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuarioExistente = usuarioDb.getUsuario(correo);
        if (usuarioExistente != null) {
            Toast.makeText(RegistroActivity.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombreUsuario, correo, contrasena);

        long resultado = usuarioDb.insertUsuario(nuevoUsuario);
        if (resultado != -1) {
            Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(RegistroActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        }

        Aplicacion app = (Aplicacion) getApplication();
        app.getUsuarios().add(nuevoUsuario);
    }

    private void btnIngresar(){
        finish();
    }
}


