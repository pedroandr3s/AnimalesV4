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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // Verifica si ya hay un usuario autenticado al iniciar la actividad
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Si hay un usuario autenticado, redirige a la actividad de inicio
            Intent intent = new Intent(InicioSesion.this, Inicio.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual para que no se pueda volver atrás con el botón de retroceso
        }

        Button botonregistro = findViewById(R.id.registro);
        Button botonregistro2 = findViewById(R.id.registro2);
        Button botoniniciarsesion = findViewById(R.id.btninicio);
        EditText correoEditText = findViewById(R.id.correo);
        EditText contraseñaEditText = findViewById(R.id.contraseña);

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
                                        Toast.makeText(InicioSesion.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                        // Redirige a la actividad de inicio
                                        Intent intent = new Intent(InicioSesion.this, Inicio.class);
                                        startActivity(intent);
                                        finish(); // Finaliza la actividad actual
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
                String correo = correoEditText.getText().toString();
                String contraseña = contraseñaEditText.getText().toString();

                if (!correo.isEmpty() && !contraseña.isEmpty()) {
                    // Intenta iniciar sesión
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Inicio de sesión exitoso, redirige a la actividad de inicio
                                        Intent intent = new Intent(InicioSesion.this, Inicio.class);
                                        startActivity(intent);
                                        finish(); // Finaliza la actividad actual
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
    }
}
