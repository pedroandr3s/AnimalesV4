    package cl.santos.animales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class DatosPajaros extends AppCompatActivity {
        private boolean edadSeleccionada = false;
        private EditText nombre;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_datos_pajaros);
            Button volverButton = findViewById(R.id.volver2);
            nombre = findViewById(R.id.nombreotro3);

            volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatosPajaros.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final LinearLayout lyEdad = findViewById(R.id.lyEdad);
        final Button botonAlimento = findViewById(R.id.alimento2);

        Button botonEdad = findViewById(R.id.btnedad3);
        botonEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hacer visible el LinearLayout "Edad"
                lyEdad.setVisibility(View.VISIBLE);
                edadSeleccionada = true;
            }
        });

        Button botonOpcion1 = findViewById(R.id.bebe);
        Button botonOpcion2 = findViewById(R.id.adulto);

        final Button btn2aldia = findViewById(R.id.btn2aldia);
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
                btn2aldia.setText("2 veces al dia");
                btn3aldia.setText("3 veces al dia");
                btn4aldia.setText("4 veces al dia");
                btn5aldia.setText("5 veces al dia(recomendado)");
            }
        });
        botonOpcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonAlimento.setVisibility(View.VISIBLE); // Mostrar el botón "Alimento"

                btn2aldia.setText("2 al día ");
                btn3aldia.setText("3 al día ");
                btn4aldia.setText("4 al día ");
                btn5aldia.setText("5 al día ");
            }
        });

        Button botonAlimento2 = findViewById(R.id.btn2aldia);
        Button botonAlimento3 = findViewById(R.id.btn3aldia);
        Button botonAlimento4 = findViewById(R.id.btn4aldia);
        Button botonAlimento5 = findViewById(R.id.btn5aldia);

        final Button siguienteButton = findViewById(R.id.siguiente2);

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
                guardarInformacionEnFirebase();

                Intent intent = new Intent(DatosPajaros.this, MainActivity.class);
                startActivity(intent);
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
                    Toast.makeText(DatosPajaros.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    // Datos guardados con éxito
                    Toast.makeText(DatosPajaros.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void enviarInformacionAlActivitySiguiente(int botonSeleccionado) {
            Intent intent = new Intent(DatosPajaros.this, Alarma.class);
            intent.putExtra("boton_seleccionado", botonSeleccionado);

            if (botonSeleccionado == 2) {
                intent.putExtra("cantidad_alimentacion", 2);
            } else if (botonSeleccionado == 3) {
                intent.putExtra("cantidad_alimentacion", 3);
            } else if (botonSeleccionado == 4) {
                intent.putExtra("cantidad_alimentacion", 4);
            } else if (botonSeleccionado == 5) {
                intent.putExtra("cantidad_alimentacion", 5);
            }

            startActivity(intent);
            finish();
        }
    }