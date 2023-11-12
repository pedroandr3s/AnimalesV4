package cl.santos.animales;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Obtén la referencia del ImageView
        ImageView cargaImageView = findViewById(R.id.carga);

        // Carga el GIF utilizando Glide
        Glide.with(this).asGif().load(R.drawable._13a3272124cc57ba9e9fb7f59e9ab3b).into(cargaImageView);

        // Ajusta el tiempo de espera según la duración del GIF (en este caso, 5000 milisegundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crea un intent para iniciar InicioSesion.class
                Intent intent = new Intent(Splash.this, InicioSesion.class);
                startActivity(intent);

                // Cierra la actividad actual
                finish();
            }
        }, 1333); // Ajusta el tiempo de espera aquí
    }
}
