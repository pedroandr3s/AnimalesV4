package cl.santos.animales;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarma extends AppCompatActivity {
    private Button btnSeleccionarHora1, btnSeleccionarHora2, btnSeleccionarHora3, btnSeleccionarHora4, btnSeleccionarHora5;
    private Button btnGuardarAlarma;
    private List<Calendar> alarmas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        TextView textViewValores = findViewById(R.id.textViewValores);

        Intent intent = getIntent();
        int botonSeleccionado = intent.getIntExtra("boton_seleccionado", 0);
        int numeroAlimentoSeleccionado = intent.getIntExtra("numero_alimento_seleccionado", 0);

// Mostrar los valores en el TextView
        String texto = "Botón Seleccionado: " + botonSeleccionado + "\nNúmero de Alimento Seleccionado: " + numeroAlimentoSeleccionado;
        textViewValores.setText(texto);


        btnSeleccionarHora1 = findViewById(R.id.btnSeleccionarHora1);
        btnSeleccionarHora2 = findViewById(R.id.btnSeleccionarHora2);
        btnSeleccionarHora3 = findViewById(R.id.btnSeleccionarHora3);
        btnSeleccionarHora4 = findViewById(R.id.btnSeleccionarHora4);
        btnSeleccionarHora5 = findViewById(R.id.btnSeleccionarHora5);

        btnGuardarAlarma = findViewById(R.id.btnGuardarAlarma);

        btnSeleccionarHora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorHora(btnSeleccionarHora1);
            }
        });
        btnSeleccionarHora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorHora(btnSeleccionarHora2);
            }
        });
        btnSeleccionarHora3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorHora(btnSeleccionarHora3);
            }
        });
        btnSeleccionarHora4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorHora(btnSeleccionarHora4);
            }
        });
        btnSeleccionarHora5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorHora(btnSeleccionarHora5);
            }
        });

        btnGuardarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                programarAlarmas();
                Intent intent = new Intent(Alarma.this, Inicio.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarSelectorHora(final Button btnSeleccionarHora) {
        // Mostrar un TimePickerDialog para seleccionar la hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(Alarma.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar selectedDateTime = Calendar.getInstance();
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDateTime.set(Calendar.MINUTE, minute);

                // Actualizar el TextView con la hora seleccionada
                actualizarTextViewHora(btnSeleccionarHora, selectedDateTime);

                // Guardar la alarma en la lista correspondiente
                alarmas.add(selectedDateTime);
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

    private void actualizarTextViewHora(Button btnSeleccionarHora, Calendar selectedDateTime) {
        String horaSeleccionada = android.text.format.DateFormat.format("HH:mm", selectedDateTime).toString();
        btnSeleccionarHora.setText(horaSeleccionada);
    }

    private void programarAlarmas() {
        // Obtener una instancia de AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Recorrer la lista de alarmas y programar cada una de ellas
        for (int i = 0; i < alarmas.size(); i++) {
            Calendar alarmTime = alarmas.get(i);

            // Crear un Intent que se enviará cuando suene la alarma
            Intent intent = new Intent(this, AlarmReceiver.class);
            int alarmId = (int) System.currentTimeMillis() + i; // Identificador único basado en la marca de tiempo actual
            intent.putExtra("ALARM_ID", alarmId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, PendingIntent.FLAG_IMMUTABLE);

            // Configurar el calendario con la hora y minuto seleccionados
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTime.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, alarmTime.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);

            // Programar la alarma
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            // Mostrar un mensaje con Toast
            Toast.makeText(this, "Alarma programada para: " + android.text.format.DateFormat.format("HH:mm", calendar), Toast.LENGTH_SHORT).show();

            // Agregar logs de depuración
            Log.d("Alarma", "Alarma programada para: " + calendar.getTime().toString());
        }
    }
}
