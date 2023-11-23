package cl.santos.animales;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatosPerro extends AppCompatActivity {
    private boolean edadSeleccionada = false;
    private EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_perro);

        Button volverButton = findViewById(R.id.Volver);
        nombre= findViewById(R.id.nombreotro);
        volverButton.setOnClickListener(view -> {
            Intent intent = new Intent(DatosPerro.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        final LinearLayout lyEdad = findViewById(R.id.lyEdad);
        final Button botonAlimento = findViewById(R.id.Alimento);

        Button botonEdad = findViewById(R.id.btnedad);
        botonEdad.setOnClickListener(view -> {
            // Hacer visible el LinearLayout "Edad"
            lyEdad.setVisibility(View.VISIBLE);
            edadSeleccionada = true;
        });

        Button botonOpcion1 = findViewById(R.id.bebe);
        Button botonOpcion2 = findViewById(R.id.adulto2);
        Button botonOpcion3 = findViewById(R.id.viejo);
        Button botonOpcion4 = findViewById(R.id.adulto);

        @SuppressLint("CutPasteId") final Button btn2aldia = findViewById(R.id.btn2aldia);
        final Button btn3aldia = findViewById(R.id.btn3aldia);
        final Button btn4aldia = findViewById(R.id.btn4aldia);
        final Button btn5aldia = findViewById(R.id.btn5aldia);

        View.OnClickListener opcionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edadSeleccionada) {
                    botonAlimento.setVisibility(View.VISIBLE);
                }
            }
        };

        botonOpcion1.setOnClickListener(opcionClickListener);
        botonOpcion2.setOnClickListener(opcionClickListener);
        botonOpcion3.setOnClickListener(opcionClickListener);
        botonOpcion4.setOnClickListener(opcionClickListener);

        final LinearLayout cantidadLayout = findViewById(R.id.lyAlimento);

        botonAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidadLayout.setVisibility(View.VISIBLE);
            }
        });

        botonOpcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonAlimento.setVisibility(View.VISIBLE);

                // Cambiar el texto de los botones
                btn2aldia.setText("Solo leche materna");
                btn3aldia.setText("Solo leche materna");
                btn4aldia.setText("Solo leche materna");
                btn5aldia.setText("Solo leche materna");
            }
        });

        botonOpcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonAlimento.setVisibility(View.VISIBLE);

                btn2aldia.setText("2 al día ");
                btn3aldia.setText("3 al día (recomendado)");
                btn4aldia.setText("4 al día (recomendado)");
                btn5aldia.setText("5 al día ");
            }
        });

        botonOpcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonAlimento.setVisibility(View.VISIBLE);

                btn2aldia.setText("2 al día (recomendado)");
                btn3aldia.setText("3 al día (recomendado)");
                btn4aldia.setText("4 al día ");
                btn5aldia.setText("5 al día ");
            }
        });

        botonOpcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonAlimento.setVisibility(View.VISIBLE);

                // Cambiar el texto de los botones
                btn2aldia.setText("2 al día (recomendado)");
                btn3aldia.setText("3 al día ");
                btn4aldia.setText("4 al día ");
                btn5aldia.setText("5 al día ");

            }
        });

        Button botonAlimento2 = findViewById(R.id.btn2aldia);
        Button botonAlimento3 = findViewById(R.id.btn3aldia);
        Button botonAlimento4 = findViewById(R.id.btn4aldia);
        Button botonAlimento5 = findViewById(R.id.btn5aldia);
        botonAlimento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarInformacionAlActivitySiguiente(2);

            }
        });
        botonAlimento3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarInformacionAlActivitySiguiente(3);

            }
        });
        botonAlimento4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarInformacionAlActivitySiguiente(4);

            }
        });
        botonAlimento5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarInformacionAlActivitySiguiente(5);

            }
        });
        final Button siguienteButton = findViewById(R.id.Siguiente);

        View.OnClickListener alimentoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siguienteButton.setVisibility(View.VISIBLE);
            }
        };

        botonAlimento5.setOnClickListener(alimentoClickListener);
        botonAlimento4.setOnClickListener(alimentoClickListener);
        botonAlimento2.setOnClickListener(alimentoClickListener);
        botonAlimento3.setOnClickListener(alimentoClickListener);

        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatosPerro.this, Alarma.class);

                startActivity(intent);

                guardarInformacionEnFirebase();



            }
        });

    }


    private void guardarInformacionEnFirebase() {
        String Nombre = nombre.getText().toString();

        // Inicializa la referencia a la base de datos Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Nombre");

        // Crea un nuevo nodo con una clave única y establece los datos
        DatabaseReference nuevoNombreRef = ref.push();
        nuevoNombreRef.child("Nombre").setValue(Nombre, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                // Handle the error here, e.g., Log.e("FirebaseError", databaseError.getMessage());
                Toast.makeText(DatosPerro.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
            } else {
                // Datos guardados con éxito
                Toast.makeText(DatosPerro.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarInformacionAlActivitySiguiente(int botonSeleccionado) {
        Intent intent = new Intent(DatosPerro.this, Alarma.class);
        intent.putExtra("boton_seleccionado", botonSeleccionado);
        startActivity(intent);
        finish();
    }
}



