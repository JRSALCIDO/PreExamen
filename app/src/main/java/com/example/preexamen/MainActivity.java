package com.example.preexamen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import modelo.UsuariosDb;

public class MainActivity extends AppCompatActivity {

    private EditText txtCorreo, txtContrasena;
    private Button btnLogin, btnRegistrarse, btnCerrar;
    private UsuariosDb usuarioDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIngresar();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegistrarse();
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCerrar();
            }
        });
    }

    private void inicializarComponentes() {
        txtCorreo = findViewById(R.id.editTextEmail);
        txtContrasena = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrarse = findViewById(R.id.btnRegister);
        btnCerrar = findViewById(R.id.btnExit);
        usuarioDb = new UsuariosDb(getApplicationContext());
    }

    private void btnIngresar() {
        String correo = txtCorreo.getText().toString();
        String contrasena = txtContrasena.getText().toString();

        Usuario usuario = usuarioDb.getUsuario(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            Intent intent = new Intent(MainActivity.this, ListaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnRegistrarse() {
        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(intent);
    }

    private void btnCerrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de que quieres cerrar la aplicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtCorreo.setText("");
        txtContrasena.setText("");
    }
}
