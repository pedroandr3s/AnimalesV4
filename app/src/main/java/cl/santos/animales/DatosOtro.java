package cl.santos.animales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class DatosOtro extends AppCompatActivity {
    private boolean edadSeleccionada = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_otros);
        Button volverButton = findViewById(R.id.volver3);

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

        final Button siguienteButton = findViewById(R.id.siguiente3);

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
                Intent intent = new Intent(DatosOtro.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}