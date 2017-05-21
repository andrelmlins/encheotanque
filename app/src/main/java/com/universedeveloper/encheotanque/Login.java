package com.universedeveloper.encheotanque;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity implements View.OnClickListener, FacebookCallback<LoginResult>,GraphRequest.GraphJSONObjectCallback, Response.ErrorListener, Response.Listener<JSONObject> {
    private ProgressDialog progress;
    private CallbackManager callbackManager;
    private Requests mRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("usuario",0);
        if (!preferences.getString("nome", "").equals("")) {
            Intent intent = new Intent(this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        this.mRequests = Requests.getInstance(this);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            afterLogin(accessToken);
        }
        callbackManager = CallbackManager.Factory.create();
        Button facebook = (Button) this.findViewById(R.id.facebook);
        facebook.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void afterLogin(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture.type(large),cover,birthday,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        progress = ProgressDialog.show(this, "Carregando...", "Por favor, aguarde", true);
        afterLogin(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException error) {
        //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        this.mRequests.post(mRequests.ROOT + "login.php", object,this,this);
    }

    @Override
    public void onResponse(JSONObject response){
        if(this.progress!=null) this.progress.dismiss();
        try {
            SharedPreferences settings = getSharedPreferences("usuario",0);
            SharedPreferences.Editor edit = settings.edit();
            edit.putString("nome",response.getString("nome"));
            edit.putString("link",response.getString("link"));
            edit.putString("email",response.getString("email"));
            edit.putString("foto",response.getString("foto"));
            edit.putString("genero",response.getString("genero"));
            edit.putString("id",response.getString("id"));
            edit.commit();
            Intent intent = new Intent(this, Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(this.progress!=null)this.progress.dismiss();
        //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.facebook){
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, this);
        }
    }
}
