package com.universedeveloper.encheotanque.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.utils.Requests;

import net.kjulio.rxlocation.RxLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by AndreLucas on 04/09/2016.
 */
public class MainFragment extends Fragment implements Action1<Location>, OnMapReadyCallback, DirectionCallback, GoogleMap.OnMarkerClickListener, Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {
    private MapView mMapView;
    private GoogleMap googleMap;
    private Location location;
    public static LatLng myPosition;
    private Requests request;
    private ProgressDialog progress;
    private LocationRequest defaultLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.favorito).setVisible(false);
        menu.findItem(R.id.add).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {
        this.rootView = inflater.inflate(R.layout.main_fragment, container, false);
        getActivity().setTitle("Enche o Tanque");

        FloatingActionButton star = (FloatingActionButton) this.rootView.findViewById(R.id.star);
        star.setOnClickListener(this);
        this.request = Requests.getInstance(getActivity());

        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(manager.isProviderEnabled( LocationManager.GPS_PROVIDER)){
            this.progress = ProgressDialog.show(getActivity(), "", "Capturando sua localização", true);
            RxLocation.locationUpdates(getActivity(), defaultLocationRequest)
                    .first()
                    .subscribe(this,new Action1<Throwable>() {
                        @Override
                        public  void call(Throwable throwable) {
                            Log.e("ERRO","asd");
                        }
                    });
        }
        return this.rootView;
    }

    public void onMapReady(GoogleMap googleMap) {
        if (this.location != null) {
            double latitude = this.location.getLatitude();
            double longitude = this.location.getLongitude();
            this.myPosition = new LatLng(latitude, longitude);
            this.request.getObject(Requests.ROOT+"/route.php?distance=20&latitude="+latitude+"&longitude="+longitude,this,this);
            this.googleMap = googleMap;
            this.googleMap.setOnMarkerClickListener(this);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.myPosition, 14));
            googleMap.addMarker(new MarkerOptions()
                    .title("voce")
                    .position(this.myPosition))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my));
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray result = response.getJSONArray("postos");
            for (int i = 0; i < result.length(); i++) {
                JSONObject posto = result.getJSONObject(i);
                LatLng position = new LatLng(Double.parseDouble(posto.getString("latitude")), Double.parseDouble(posto.getString("longitude")));
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .title(posto.getString("hash"))
                        .position(position));
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon));
            }
            if(this.progress!=null && this.progress.isShowing()) this.progress.dismiss();
        } catch (JSONException e) {
            if(this.progress!=null && this.progress.isShowing()) this.progress.dismiss();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.getTitle().equals("voce")) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            PostoFragment f = new PostoFragment();
            Bundle b = new Bundle();
            b.putString("hash", marker.getTitle());
            b.putString("latitude", this.myPosition.latitude + "");
            b.putString("longitude", this.myPosition.longitude + "");
            f.setArguments(b);
            fm.beginTransaction().replace(R.id.content_fragment, f).addToBackStack("").commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Route route = direction.getRouteList().get(0);
        Leg leg = route.getLegList().get(0);
        ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
        PolylineOptions polylineOptions = DirectionConverter.createPolyline(this.getActivity().getApplicationContext(), directionPositionList, 5, Color.RED);
        this.googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(this.progress!=null) this.progress.dismiss();
    }

    @Override
    public void onClick(View v) {
        this.googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .title("voce")
                .position(this.myPosition))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my));
        this.progress = ProgressDialog.show(getActivity(), "", "Carregar postos mais em conta", true);
        this.request.getObject(this.request.ROOT+"star.php?distance=5&combustivel=Gasolina&latitude="+this.location.getLatitude()+"&longitude="+this.location.getLongitude(),this,this);

    }

    @Override
    public void call(Location location) {
        this.location = location;
        this.mMapView = (MapView) rootView.findViewById(R.id.map);
        this.mMapView.onCreate(null);
        this.mMapView.onResume();
        this.mMapView.getMapAsync(this);
        if(this.progress!=null) this.progress.setMessage("Marcando os postos próximos");
    }
}
