package co.cargo.aleksa.ridesmap;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import co.cargo.aleksa.ridesmap.pojo.RidePoint;
import co.cargo.aleksa.ridesmap.gson.RidePointDeserializer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Observable<RidePoint> mRidesObservable;

    private float distance = 0;
    private LatLng lastLocation = null;

    private float[] results = new float[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mRidesObservable = Observable.from(getRidePoints());

        ((Button)findViewById(R.id.distance_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, String.valueOf(distance), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Action1<RidePoint> observer = new Action1<RidePoint>() {
            @Override
            public void call(RidePoint ridePoint) {
                LatLng newLocation = new LatLng(ridePoint.getLatitude(), ridePoint.getLongitude());
                mMap.addMarker(new MarkerOptions().position(newLocation).title(ridePoint.getTimeCreated()));
                if (lastLocation != null) {
                    Location.distanceBetween(
                            lastLocation.latitude, lastLocation.longitude,
                            newLocation.latitude, newLocation.longitude,
                            results);
                    distance += results[0];
                }
                lastLocation = newLocation;
            }
        };

        mRidesObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private String readRawJson(){
        InputStream is = getResources().openRawResource(R.raw.ride_coords);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder returnString = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                returnString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString.toString();
    }

    private RidePoint[] getRidePoints(){
        final Gson gson = new GsonBuilder().registerTypeAdapter(RidePoint.class, new RidePointDeserializer()).create();
        return gson.fromJson(readRawJson(), RidePoint[].class);
    }
}
