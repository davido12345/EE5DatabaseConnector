package com.example.david.ee5testapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.ee5testapplication.Databases.InfoArrays;
import com.example.david.ee5testapplication.Databases.Keys;
import com.example.david.ee5testapplication.Databases.Links;

import java.util.zip.InflaterOutputStream;

/**
 * A login screen that offers login via email/password.
 */

    public class LoginActivity extends AppCompatActivity{

        EditText UsernameEt, PasswordEt;
        TextView DBQuery;
        String TAG = "David: ";

    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_login);
        init();
            UsernameEt = (EditText)findViewById(R.id.userNameBox);
            PasswordEt = (EditText)findViewById(R.id.passwordBox);
            DBQuery = (TextView)findViewById(R.id.textView1);
            init();

        }
        public void OnLogin(View view) {
            String username = UsernameEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String type = "login";
        }


    private void init() {

        Log.d(TAG, "about to start volley");
        JSonVolley(Links.allMowerData);
        JSonVolley(Links.allSessionData);
        JSonVolley(Links.allSessions);
        Log.d(TAG, "finished volley");

    Button Map = (Button) findViewById(R.id.signInButton);
        Map.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Context context = getApplicationContext();
            CharSequence text = "Hello toast!"+ InfoArrays.GPS_XSD.get(1);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            }
        });
    }
    private void JSonVolley(final String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "got a response");
                //manipulate response
                try {
                    for(int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        try {
                            JSonToArray(jsonObject, url);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }

    private void JSonToArray (JSONObject jsonObject, String url) throws Exception {
        if(url.equals(Links.allMowerData)) {
            InfoArrays.type_Mower.add(jsonObject.getString(Keys.Type));
            Log.d(TAG, "TYPE INPUT");
            InfoArrays.id_Mower.add(jsonObject.getInt(Keys.id_Mower));
            InfoArrays.id_workGroup.add(jsonObject.getInt(Keys.id_workGround));
            InfoArrays.name_Mower.add(jsonObject.getString(Keys.name_Mower));

        }else if(url.equals(Links.allSessionData)) {

            InfoArrays.id_dataSD.add(jsonObject.getInt(Keys.session_data_id));
            InfoArrays.id_MowerSD.add(jsonObject.getString(Keys.session_mower_id));
            InfoArrays.timeSD.add(jsonObject.getString(Keys.session_data_time));
            InfoArrays.GPS_XSD.add(jsonObject.getDouble(Keys.session_data_Gps_x));
            InfoArrays.GPS_YSD.add(jsonObject.getDouble(Keys.session_data_Gps_y));
            InfoArrays.JoystickSD.add(jsonObject.getDouble(Keys.session_data_Joystic));
            InfoArrays.Oil_TempSD.add(jsonObject.getDouble(Keys.session_data_Oil_temp));
            InfoArrays.Acc_X_1SD.add(jsonObject.getDouble(Keys.session_data_Acc_x_1));
            InfoArrays.Acc_Y_1SD.add(jsonObject.getDouble(Keys.session_data_Acc_y_1));
            InfoArrays.Acc_Z_1SD.add(jsonObject.getDouble(Keys.session_data_Acc_z_1));
            InfoArrays.Pitch_1SD.add(jsonObject.getDouble(Keys.session_data_Pitch_1));
            InfoArrays.Yaw_1SD.add(jsonObject.getDouble(Keys.session_data_Yaw_1));
            InfoArrays.Roll_1SD.add(jsonObject.getDouble(Keys.session_data_Roll_1));

        }else if(url.equals(Links.allSessions)) {

            InfoArrays.id_sess.add(jsonObject.getInt(Keys.id_sess));
            InfoArrays.id_MowerS.add(jsonObject.getInt(Keys.id_Mower));
            InfoArrays.dateS.add(jsonObject.getString(Keys.session_date));
            InfoArrays.startTimeS.add(jsonObject.getString(Keys.session_startTime));
            InfoArrays.Duration.add(jsonObject.getString(Keys.session_Duration));
        }
        //Log.d(TAG, "getting size :" + InfoArrays.firstNames.size());

    }
}


