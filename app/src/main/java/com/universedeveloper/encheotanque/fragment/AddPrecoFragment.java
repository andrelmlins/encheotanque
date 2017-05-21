package com.universedeveloper.encheotanque.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AndreLucas on 15/10/2016.
 */
public class AddPrecoFragment extends Fragment implements AdapterView.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private View rootView;
    private Requests r;
    private String posto;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.favorito).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.add_preco_fragment, container, false);
        Button button = (Button) this.rootView.findViewById(R.id.add);
        button.setOnClickListener(this);
        getActivity().setTitle("Adicionar Preço");
        this.posto = getArguments().getString("hash");
        this.r = Requests.getInstance(getActivity());
        return this.rootView;
    }

    @Override
    public void onClick(View v) {
        this.progress = ProgressDialog.show(getActivity(), "","Enviando os preços...", true);
        EditText gasolina = (EditText) this.rootView.findViewById(R.id.precog);
        EditText etanol = (EditText) this.rootView.findViewById(R.id.precoe);
        EditText diesel = (EditText) this.rootView.findViewById(R.id.precod);
        EditText diesels10 = (EditText) this.rootView.findViewById(R.id.precods);
        EditText gnv = (EditText) this.rootView.findViewById(R.id.precogn);
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
        JSONObject json = new JSONObject();
        try {
            json.put("gasolina",gasolina.getText());
            json.put("etanol",etanol.getText());
            json.put("diesel",diesel.getText());
            json.put("diesels10",diesels10.getText());
            json.put("gnv",gnv.getText());
            json.put("usuario",preferences.getString("id",""));
            json.put("posto",this.posto);
            r.post(Requests.ROOT+"/adicionarprecoposto.php",json,this,this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.progress.dismiss();
        Toast.makeText(getActivity(), error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        PostoFragment f = new PostoFragment();
        Bundle b = new Bundle();
        b.putString("hash",posto);
        f.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,f).commit();
        this.progress.dismiss();
    }
}
