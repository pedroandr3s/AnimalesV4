package cl.santos.animales;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Encuentra el ImageButton con ID "otro"
        ImageButton botonOtro = findViewById(R.id.ibOtro);
        ImageButton botonPerro = findViewById(R.id.ibPerro);
        ImageButton botonGato = findViewById(R.id.ibGato);
        ImageButton botonCanario = findViewById(R.id.ibCanario);

         Glide.with(this).asGif().load(R.drawable._32c2495abbd9ec400f29ac549170c77).into(botonCanario);

        // Configura un OnClickListener para el ImageButton
        botonOtro.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad DatosOtroActivity
            Intent intent = new Intent(MainActivity.this, DatosPerro.class);
            startActivity(intent);
        });
        botonPerro.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad DatosPerro
            Intent intent = new Intent(MainActivity.this, DatosPerro.class);
            startActivity(intent);
        });

        botonGato.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad DatosOtroActivity
            Intent intent = new Intent(MainActivity.this, DatosCats.class);
            startActivity(intent);
        });
        botonCanario.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad DatosOtroActivity
            Intent intent = new Intent(MainActivity.this, DatosPajaros.class);
            startActivity(intent);
        });

    }
}
