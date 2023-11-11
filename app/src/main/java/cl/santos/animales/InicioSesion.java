package cl.santos.animales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InicioSesion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        Button botonregistro = findViewById(R.id.registro);
        Button botoniniciarsesion = findViewById(R.id.btninicio);
        EditText correoEditText = findViewById(R.id.correo);
        EditText contraseñaEditText = findViewById(R.id.contraseña);
        String correo = correoEditText.getText().toString();
        String contraseña = contraseñaEditText.getText().toString();
        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Encuentra los EditText por sus ID
                EditText correoEditText = findViewById(R.id.correo);
                EditText contraseñaEditText = findViewById(R.id.contraseña);

                // Obtiene el texto de los EditText
                String correo = correoEditText.getText().toString();
                String contraseña = contraseñaEditText.getText().toString();

                if (!correo.isEmpty() && !contraseña.isEmpty()) {
                    // Los campos de correo y contraseña no están vacíos
                    // Intenta crear una cuenta de usuario
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Registro exitoso, puedes redirigir a otra actividad o realizar otras acciones
                                        Intent intent = new Intent(InicioSesion.this, Inicio.class);
                                        startActivity(intent);
                                    } else {
                                        // Registro fallido, muestra un mensaje de error
                                        Toast.makeText(InicioSesion.this, "Registro fallido: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Los campos de correo o contraseña están vacíos
                    Toast.makeText(InicioSesion.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        botoniniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Encuentra los EditText por sus ID
                EditText correoEditText = findViewById(R.id.correo);
                EditText contraseñaEditText = findViewById(R.id.contraseña);

                // Obtiene el texto de los EditText
                String correo = correoEditText.getText().toString();
                String contraseña = contraseñaEditText.getText().toString();

                if (!correo.isEmpty() && !contraseña.isEmpty()) {
                    // Los campos de correo y contraseña no están vacíos
                    // Intenta iniciar sesión
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Inicio de sesión exitoso, puedes redirigir a otra actividad o realizar otras acciones
                                        Intent intent = new Intent(InicioSesion.this, Inicio.class);
                                        startActivity(intent);
                                    } else {
                                        // Inicio de sesión fallido, muestra un mensaje de error
                                        Toast.makeText(InicioSesion.this, "Inicio de sesión fallido: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Los campos de correo o contraseña están vacíos
                    Toast.makeText(InicioSesion.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }}
