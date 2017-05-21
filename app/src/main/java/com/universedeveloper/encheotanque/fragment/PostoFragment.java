package com.universedeveloper.encheotanque.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Favorito;
import com.universedeveloper.encheotanque.utils.Preferencias;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AndreLucas on 12/09/2016.
 */
public class PostoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener, OnMapReadyCallback, DirectionCallback,  MenuItem.OnMenuItemClickListener {

    private View rootView;
    private MapView mMapView;
    private LatLng center;
    private JSONObject posto;
    private ProgressDialog progress;
    private LatLng position;
    private GoogleMap googleMap;
    private Preferencias p;
    private Favorito favorito;
    private Menu menu;
    private Requests r;
    private boolean addPref = false;
    private boolean rmPref = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        MenuItem add = menu.findItem(R.id.add);
        add.setOnMenuItemClickListener(this);
        add.setVisible(true);
        MenuItem star = menu.findItem(R.id.favorito);
        star.setOnMenuItemClickListener(this);
        if(this.p!=null){
            if(this.p.isPref(this.favorito)) star.setIcon(getResources().getDrawable(R.drawable.ic_star));
            else star.setIcon(getResources().getDrawable(R.drawable.ic_star_border));
        }
        star.setVisible(true);
        this.menu = menu;
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        this.rootView = inflater.inflate(R.layout.posto_fragment  ,container,false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando o posto...", true);
        String posto = getArguments().getString("hash");
        this.position = MainFragment.myPosition;
        p = Preferencias.getInstance(this.getActivity());
        favorito = new Favorito("posto",posto," ");
        this.r = Requests.getInstance(getActivity());
        this.r.getObject(Requests.ROOT+"/posto.php?hash="+posto,this,this);
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(this.progress!=null) this.progress.dismiss();
        Toast.makeText(getActivity(), volleyError.toString(),
                Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onResponse(JSONObject response) {
        if(this.addPref){
            this.p.add(this.favorito);
            MenuItem star = menu.findItem(R.id.favorito);
            star.setIcon(getResources().getDrawable(R.drawable.ic_star));
            this.addPref = false;
            if(this.progress!=null) this.progress.dismiss();
        }
        else if(this.rmPref){
            this.p.remove(this.favorito);
            MenuItem star = menu.findItem(R.id.favorito);
            star.setIcon(getResources().getDrawable(R.drawable.ic_star_border));
            this.rmPref = false;
            if(this.progress!=null) this.progress.dismiss();
        }
        else {
            try {
                this.posto = response.getJSONObject("posto");
                getActivity().setTitle(this.posto.getString("razao"));
                JSONArray precos = this.posto.getJSONArray("precos");

                TextView title = (TextView) this.rootView.findViewById(R.id.nome);
                TextView endereco = (TextView) this.rootView.findViewById(R.id.endereco);
                TextView cidade = (TextView) this.rootView.findViewById(R.id.cidade);
                ImageView image = (ImageView) this.rootView.findViewById(R.id.image);

                title.setText(this.posto.getString("razao"));
                endereco.setText(this.posto.getString("endereco") + " - " + this.posto.getString("bairro"));
                cidade.setText(this.posto.getString("cidade") + " - " + this.posto.getString("estadoabrev"));
                Picasso.with(rootView.getContext()).load(this.posto.getString("logo")).into(image);

                LinearLayout l = (LinearLayout) this.rootView.findViewById(R.id.listfuel);
                LinearLayout lchild = (LinearLayout) l.findViewById(R.id.listele);
                TextView t = (TextView) lchild.findViewById(R.id.preco);
                TextView t1 = (TextView) lchild.findViewById(R.id.fuel);
                l.removeAllViews();

                for (int i = 0; i < precos.length(); i++) {
                    JSONObject preco = precos.getJSONObject(i);
                    LinearLayout lchild_aux = new LinearLayout(getContext());
                    TextView t_aux = new TextView(getContext());
                    TextView t1_aux = new TextView(getContext());
                    TextView t2_aux = new TextView(getContext());

                    lchild_aux.setLayoutParams(lchild.getLayoutParams());
                    lchild_aux.setOrientation(lchild.getOrientation());
                    lchild_aux.setWeightSum(0.2f);
                    lchild_aux.setPadding(5, 5, 5, 5);

                    t_aux.setLayoutParams(t.getLayoutParams());
                    t_aux.setTextSize(14);
                    t_aux.setTextColor(t.getTextColors());
                    t_aux.setBackground(t.getBackground());
                    t_aux.setTextAlignment(t.getTextAlignment());
                    t_aux.setPadding(0, 20, 0, 20);

                    t1_aux.setLayoutParams(t1.getLayoutParams());
                    t1_aux.setTextAlignment(t1.getTextAlignment());
                    t1_aux.setTextSize(10);

                    t2_aux.setLayoutParams(t1.getLayoutParams());
                    t2_aux.setTextAlignment(t1.getTextAlignment());
                    t2_aux.setTextSize(10);

                    t_aux.setText(preco.getString("preco"));
                    t1_aux.setText(preco.getString("combustivel"));
                    t2_aux.setText(preco.getString("data"));

                    lchild_aux.addView(t_aux);
                    lchild_aux.addView(t1_aux);
                    lchild_aux.addView(t2_aux);
                    l.addView(lchild_aux);
                }

                this.center = new LatLng(Double.parseDouble(this.posto.getString("latitude")), Double.parseDouble(this.posto.getString("longitude")));

                this.mMapView = (MapView) rootView.findViewById(R.id.mapPosto);
                this.mMapView.onCreate(null);
                this.mMapView.onResume();
                this.mMapView.getMapAsync(this);

                FloatingActionButton button = (FloatingActionButton) this.rootView.findViewById(R.id.direction);
                button.setOnClickListener(this);

                progress.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.center, 15));
        try {
            googleMap.addMarker(new MarkerOptions()
                    .title(this.posto.getString("razao"))
                    .position(this.center))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon));
            this.googleMap = googleMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void direction(){
        GoogleDirection.withServerKey("AIzaSyAeeBxVE-JsDkeQbBAkw9dUx0uQGfA-joA")
                .from(this.position)
                .to(this.center)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        this.googleMap.clear();
        Route route = direction.getRouteList().get(0);
        Leg leg = route.getLegList().get(0);
        ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
        PolylineOptions polylineOptions = DirectionConverter.createPolyline(this.getActivity().getApplicationContext(), directionPositionList, 5, Color.parseColor("#E65100"));
        this.googleMap.addPolyline(polylineOptions);
        try {
            googleMap.addMarker(new MarkerOptions()
                    .title(this.posto.getString("razao"))
                    .position(this.center))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        googleMap.addMarker(new MarkerOptions()
                .title("Você")
                .position(this.position))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.position, 15));
        this.progress.dismiss();
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
        this.progress.dismiss();
    }

    @Override
    public void onClick(View v) {
        this.progress = ProgressDialog.show(getActivity(), "", "Criando uma rota até o posto...", true);
        this.direction();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.add){
            try {
                AddPrecoFragment f = new AddPrecoFragment();
                Bundle b = new Bundle();
                b.putString("hash",this.posto.getString("hash"));
                f.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,f).commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                if (!this.p.isPref(this.favorito)){
                    this.addPref = true;
                    this.progress = ProgressDialog.show(getActivity(), "","Adicionando aos favoritos...", true);
                    this.r.post(this.r.ROOT + "adicionarpreferencia.php", this.p.get(this.favorito), this, this);
                }
                else{
                    this.rmPref = true;
                    this.progress = ProgressDialog.show(getActivity(), "","Removendo dos favoritos...", true);
                    this.r.post(this.r.ROOT + "removerpreferencia.php", this.p.get(this.favorito), this, this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
