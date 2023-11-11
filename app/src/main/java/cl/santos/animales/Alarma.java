package cl.santos.animales;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


public class Alarma extends AppCompatActivity {
    private Button btnSeleccionarFechaHora;
    private Button btnGuardarAlarma;
    private TextView textViewFechaSeleccionada;
    private TextView textViewHoraSeleccionada;
    private Calendar selectedDateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        btnSeleccionarFechaHora = findViewById(R.id.btnSeleccionarFechaHora);
        btnGuardarAlarma = findViewById(R.id.btnGuardarAlarma);
        textViewFechaSeleccionada = findViewById(R.id.textViewFechaSeleccionada);
        textViewHoraSeleccionada = findViewById(R.id.textViewHoraSeleccionada);

        btnSeleccionarFechaHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorFechaHora();
            }
        });

        btnGuardarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                programarAlarma();
                Intent intent = new Intent(Alarma.this, Inicio.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void mostrarSelectorFechaHora() {
        // Mostrar un DatePickerDialog para seleccionar la fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDateTime.set(Calendar.YEAR, year);
                selectedDateTime.set(Calendar.MONTH, month);
                selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Mostrar un TimePickerDialog para seleccionar la hora
                TimePickerDialog timePickerDialog = new TimePickerDialog(Alarma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);

                        // Actualizar los TextView con la fecha y hora seleccionadas
                        actualizarTextViewsFechaHora();
                    }
                }, selectedDateTime.get(Calendar.HOUR_OF_DAY), selectedDateTime.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        }, selectedDateTime.get(Calendar.YEAR), selectedDateTime.get(Calendar.MONTH), selectedDateTime.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void actualizarTextViewsFechaHora() {
        String fechaSeleccionada = android.text.format.DateFormat.format("dd-MM-yyyy", selectedDateTime).toString();
        String horaSeleccionada = android.text.format.DateFormat.format("HH:mm", selectedDateTime).toString();

        textViewFechaSeleccionada.setText("Fecha seleccionada: " + fechaSeleccionada);
        textViewHoraSeleccionada.setText("Hora seleccionada: " + horaSeleccionada);
    }

    private void programarAlarma() {
        // Aquí debes implementar la lógica para programar la alarma utilizando la fecha y hora seleccionadas (selectedDateTime).
        // Esto puede implicar el uso de AlarmManager y PendingIntent, similar a tu código original para programar la alarma.
        // Asegúrate de definir correctamente el Intent que debe ejecutarse cuando suene la alarma.

        // Ejemplo:
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Aquí configura el calendario con selectedDateTime y programa la alarma.
        Calendar calendar = selectedDateTime;

        // Programar la alarma
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}