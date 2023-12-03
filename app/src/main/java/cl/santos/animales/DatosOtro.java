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

public class DatosOtro extends AppCompatActivity {
    private boolean edadSeleccionada = false;
    private EditText nombre;
    private int numeroAlimentoSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_otros);
        Button volverButton = findViewById(R.id.volver3);
        nombre = findViewById(R.id.nombreotro4);
        Button btn2aldia = findViewById(R.id.btn2aldia);
        Button btn3aldia = findViewById(R.id.btn3aldia);
        Button btn4aldia = findViewById(R.id.btn4aldia);
        Button btn5aldia = findViewById(R.id.btn5aldia);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatosOtro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final LinearLayout lyEdad = findViewById(R.id.lyEdad);
        final Button botonAlimento = findViewById(R.id.alimento3);

        Button botonEdad = findViewById(R.id.btnedad4);
        botonEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hacer visible el LinearLayout "Edad"
                lyEdad.setVisibility(View.VISIBLE);
                edadSeleccionada = true;
            }
        });

        Button botonOpcion1 = findViewById(R.id.bebe);
        Button botonOpcion2 = findViewById(R.id.adulto2);


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

        btn2aldia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 2;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
                guardarInformacionEnFirebase();
            }
        });

        btn3aldia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 3;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
                guardarInformacionEnFirebase();
            }
        });

        btn4aldia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 4;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
                guardarInformacionEnFirebase();
            }
        });

        btn5aldia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 5;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
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
                Toast.makeText(DatosOtro.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
            } else {
                // Datos guardados con éxito
                Toast.makeText(DatosOtro.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarInformacionAlActivitySiguiente(int botonSeleccionado) {
        Intent intent = new Intent(DatosOtro.this, Alarma.class);
        intent.putExtra("numero_alimento_seleccionado", numeroAlimentoSeleccionado);
        startActivity(intent);
    }
}