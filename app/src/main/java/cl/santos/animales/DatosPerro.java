package cl.santos.animales;

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
    private int numeroAlimentoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_perro);

        nombre = findViewById(R.id.nombreotro);

        Button btn2 = findViewById(R.id.btn2aldia);
        Button btn3 = findViewById(R.id.btn3aldia);
        Button btn4 = findViewById(R.id.btn4aldia);
        Button btn5 = findViewById(R.id.btn5aldia);

        Button volverButton = findViewById(R.id.Volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatosPerro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        LinearLayout lyEdad = findViewById(R.id.lyEdad);
        Button botonAlimento = findViewById(R.id.Alimento);

        Button botonEdad = findViewById(R.id.btnedad);
        botonEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyEdad.setVisibility(View.VISIBLE);
                edadSeleccionada = true;
            }
        });

        Button botonOpcion1 = findViewById(R.id.bebe);
        Button botonOpcion2 = findViewById(R.id.adulto2);
        Button botonOpcion3 = findViewById(R.id.viejo);
        Button botonOpcion4 = findViewById(R.id.adulto);

        Button btn2aldia = findViewById(R.id.btn2aldia);
        Button btn3aldia = findViewById(R.id.btn3aldia);
        Button btn4aldia = findViewById(R.id.btn4aldia);
        Button btn5aldia = findViewById(R.id.btn5aldia);

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

        LinearLayout cantidadLayout = findViewById(R.id.lyAlimento);

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
                btn2aldia.setText("2 al día (recomendado)");
                btn3aldia.setText("3 al día ");
                btn4aldia.setText("4 al día ");
                btn5aldia.setText("5 al día ");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 2;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
                guardarInformacionEnFirebase();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 3;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
                guardarInformacionEnFirebase();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroAlimentoSeleccionado = 4;
                enviarInformacionAlActivitySiguiente(numeroAlimentoSeleccionado);
                guardarInformacionEnFirebase();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Nombre");

        DatabaseReference nuevoNombreRef = ref.push();
        nuevoNombreRef.child("Nombre").setValue(Nombre, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                Toast.makeText(DatosPerro.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DatosPerro.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarInformacionAlActivitySiguiente(int botonSeleccionado) {
        Intent intent = new Intent(DatosPerro.this, Alarma.class);
        intent.putExtra("numero_alimento_seleccionado", numeroAlimentoSeleccionado);
        startActivity(intent);
    }
}
