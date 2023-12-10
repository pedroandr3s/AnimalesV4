package cl.santos.animales;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cl.santos.animales.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Configurar el callback de ubicación
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                    }
                }
            }
        };

        // Solicitar permisos de ubicación si no están otorgados
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Marcador 1
        LatLng sydney = new LatLng(-36.60117926277594, -72.10419695939197);
        MarkerOptions markerOptions1 = new MarkerOptions()
                .position(sydney)
                .title("Petline Chillan")
                .snippet("Gamero 399, 3780000 Chillán, Ñuble\nhttp://www.petlinechile.cl/\n+56422217710");
        mMap.addMarker(markerOptions1);

        // Marcador 2
        LatLng otroPunto = new LatLng(-36.598611048972465, -72.10087070381662);
        MarkerOptions markerOptions2 = new MarkerOptions()
                .position(otroPunto)
                .title("OrangePet")
                .snippet("Av. Ecuador 539, Chillán, Ñuble\nhttps://orangepet.cl/\n+56940924070");
        mMap.addMarker(markerOptions2);

        // Marcador 3
        LatLng otroPuntito = new LatLng(-36.4266305319023, -71.96014076451395);
        MarkerOptions markerOptions3 = new MarkerOptions()
                .position(otroPuntito)
                .title("Los Regalones")
                .snippet("Ernesto Riquelme 496-400, 3840406 San Carlos, Ñuble\nhttps://losregalones.cl/\n+569133");
        mMap.addMarker(markerOptions3);

        // Mueve la cámara al primer marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener las actualizaciones de ubicación cuando la actividad se destruye
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
