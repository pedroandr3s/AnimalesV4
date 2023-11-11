package cl.santos.animales;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    private ListView listRecibir;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> mantenimientosKeys; // Para almacenar las claves únicas de los mantenimientos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ImageButton btnMapa = findViewById(R.id.Mapa);
        ImageButton btnMas = findViewById(R.id.btnmas);
        ImageButton btnSalir = findViewById(R.id.salir);

        btnMapa.setOnClickListener(v -> {
            Intent intent = new Intent(Inicio.this, MapsActivity.class);
            startActivity(intent);
        });
        // ...

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    // Obtén el correo del usuario actualmente autenticado
                    String correoUsuario = auth.getCurrentUser().getEmail();

                    // Muestra el mensaje de salida
                    Toast.makeText(Inicio.this, "Saliendo de " + correoUsuario, Toast.LENGTH_SHORT).show();

                    // Cierra sesión
                    auth.signOut();

                    // Redirige a la pantalla de inicio de sesión
                    Intent intent = new Intent(Inicio.this, InicioSesion.class);
                    startActivity(intent);
                    finish(); // Finaliza la actividad actual
                } else {
                    // No hay usuario autenticado, realiza la redirección directamente
                    Intent intent = new Intent(Inicio.this, InicioSesion.class);
                    startActivity(intent);
                    finish(); // Finaliza la actividad actual
                }
            }
        });

        btnMas.setOnClickListener(v -> {
            Intent intent = new Intent(Inicio.this, MainActivity.class);
            startActivity(intent);
        });
        listRecibir = findViewById(R.id.listRecibir);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listRecibir.setAdapter(adapter);
        mantenimientosKeys = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("Nombre");

        mantenimientosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                mantenimientosKeys.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String mantenimientoKey = ds.getKey();
                    mantenimientosKeys.add(mantenimientoKey);
                    String nombre = ds.child("Nombre").getValue(String.class);

                    String mantenimientoData = "Nombre: " + nombre;

                    String elementoLista = mantenimientoData + " [Editar] [Eliminar]";
                    adapter.add(elementoLista);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Error al leer el valor
            }
        });

        listRecibir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String mantenimientoKey = mantenimientosKeys.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("Opciones")
                        .setItems(new CharSequence[]{"Editar", "Eliminar"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    editarMantenimiento(mantenimientoKey);
                                } else if (which == 1) {
                                    eliminarMantenimiento(mantenimientoKey);
                                }
                            }
                        });
                builder.create().show();
            }
        });
    }

    private void editarMantenimiento(final String mantenimientoKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Mantenimiento");

        final EditText nombreEditText = new EditText(this);
        nombreEditText.setHint("Nombre");

        builder.setView(nombreEditText);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("Nombre").child(mantenimientoKey);
        mantenimientosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombreActual = dataSnapshot.child("Nombre").getValue(String.class);
                nombreEditText.setText(nombreActual);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores
            }
        });

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoNombre = nombreEditText.getText().toString();

                DatabaseReference mantenimientosRef = FirebaseDatabase.getInstance().getReference("Nombre").child(mantenimientoKey);
                mantenimientosRef.child("Nombre").setValue(nuevoNombre);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada si se selecciona "Cancelar"
            }
        });

        builder.create().show();
    }

    private void eliminarMantenimiento(String mantenimientoKey) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("Nombre");

        mantenimientosRef.child(mantenimientoKey).removeValue();
    }
}
