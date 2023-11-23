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

import java.util.Calendar;

public class Alarma extends AppCompatActivity {
    private Button btnSeleccionarHora1, btnSeleccionarHora2, btnSeleccionarHora3, btnSeleccionarHora4, btnSeleccionarHora5, btnGuardarAlarma;
    private TextView textViewHoraSeleccionada1, textViewHoraSeleccionada2, textViewHoraSeleccionada3, textViewHoraSeleccionada4, textViewHoraSeleccionada5;
    private Calendar[] selectedDateTime = new Calendar[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        // Inicializar botones y horas seleccionadas
        btnSeleccionarHora1 = findViewById(R.id.btnSeleccionarHora1);
        btnSeleccionarHora2 = findViewById(R.id.btnSeleccionarHora2);
        btnSeleccionarHora3 = findViewById(R.id.btnSeleccionarHora3);
        btnSeleccionarHora4 = findViewById(R.id.btnSeleccionarHora4);
        btnSeleccionarHora5 = findViewById(R.id.btnSeleccionarHora5);
        btnGuardarAlarma = findViewById(R.id.btnGuardarAlarma);

        textViewHoraSeleccionada1 = findViewById(R.id.textViewHoraSeleccionada1);
        textViewHoraSeleccionada2 = findViewById(R.id.textViewHoraSeleccionada2);
        textViewHoraSeleccionada3 = findViewById(R.id.textViewHoraSeleccionada3);
        textViewHoraSeleccionada4 = findViewById(R.id.textViewHoraSeleccionada4);
        textViewHoraSeleccionada5 = findViewById(R.id.textViewHoraSeleccionada5);

        // Inicializar el array de horas seleccionadas
        for (int i = 0; i < 5; i++) {
            selectedDateTime[i] = Calendar.getInstance();
        }

        // Configurar eventos de clic para cada botón
        setButtonClickEvent(btnSeleccionarHora1, textViewHoraSeleccionada1, 0);
        setButtonClickEvent(btnSeleccionarHora2, textViewHoraSeleccionada2, 1);
        setButtonClickEvent(btnSeleccionarHora3, textViewHoraSeleccionada3, 2);
        setButtonClickEvent(btnSeleccionarHora4, textViewHoraSeleccionada4, 3);
        setButtonClickEvent(btnSeleccionarHora5, textViewHoraSeleccionada5, 4);
        Intent intent = getIntent();
        if (intent != null) {
            int botonSeleccionado = intent.getIntExtra("boton_seleccionado", 0);

            // Mostrar u ocultar los botones según el botón seleccionado
            mostrarOcultarBotones(botonSeleccionado);
        }

        btnGuardarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                programarAlarmas();
            }
        });

        // Actualizar los TextViewHoraSeleccionadax con las horas iniciales
        actualizarTextViewHora(textViewHoraSeleccionada1, 0);
        actualizarTextViewHora(textViewHoraSeleccionada2, 1);
        actualizarTextViewHora(textViewHoraSeleccionada3, 2);
        actualizarTextViewHora(textViewHoraSeleccionada4, 3);
        actualizarTextViewHora(textViewHoraSeleccionada5, 4);
    }

    private void setButtonClickEvent(final Button button, final TextView textView, final int index) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorHora(textView, index);
            }
        });
    }

    private void mostrarSelectorHora(final TextView textView, final int index) {
        // Mostrar un TimePickerDialog para seleccionar la hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(Alarma.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedDateTime[index].set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDateTime[index].set(Calendar.MINUTE, minute);

                // Actualizar el TextView con la hora seleccionada
                actualizarTextViewHora(textView, index);
            }
        }, selectedDateTime[index].get(Calendar.HOUR_OF_DAY), selectedDateTime[index].get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

    private void actualizarTextViewHora(TextView textView, int index) {
        String horaSeleccionada = android.text.format.DateFormat.format("HH:mm", selectedDateTime[index]).toString();
        textView.setText("La hora del botón " + (index + 1) + " (btnSeleccionarHora" + (index + 1) + ") es: " + horaSeleccionada);
    }

    private void programarAlarmas() {
        // Obtener una instancia de AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Crear un Intent que se enviará cuando suene la alarma
        Intent intent = new Intent(this, AlarmReceiver.class);

        for (int i = 0; i < 5; i++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i + 1, intent, PendingIntent.FLAG_IMMUTABLE);

            // Configurar el calendario con la fecha y hora seleccionadas
            Calendar calendar = selectedDateTime[i];

            // Programar cada alarma
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            // Agregar logs de depuración
            Log.d("Alarma", "Alarma programada para el botón " + (i + 1) + " para: " + calendar.getTime().toString());
        }

        // Mostrar un mensaje con Toast
        Toast.makeText(this, "Alarmas programadas", Toast.LENGTH_SHORT).show();
    }

    private void mostrarOcultarBotones(int botonSeleccionado) {
        // Obtener referencias a los botones btnSeleccionarHora
        Button[] botonesHora = new Button[]{
                btnSeleccionarHora1, btnSeleccionarHora2, btnSeleccionarHora3, btnSeleccionarHora4, btnSeleccionarHora5
        };

        // Ocultar todos los botones primero
        for (Button btn : botonesHora) {
            btn.setVisibility(View.GONE);
        }

        // Mostrar solo los botones seleccionados
        for (int i = 0; i < botonSeleccionado; i++) {
            botonesHora[i].setVisibility(View.VISIBLE);
        }
    }
}
