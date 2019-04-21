package com.bionime.bionimedemo.modules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bionime.bionimedemo.BaseContracts;
import com.bionime.bionimedemo.BasedbContracts;
import com.bionime.bionimedemo.sqlite.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BionimeModules implements BionimeContracts.Model, BasedbContracts {
    private BionimeContracts.ModelOutput output;
    private Context context;
    private String TAG = "Log-> Modules";


    BionimeModules(Context context){
        this.context = context;
    }
    @Override
    public void setupPresenter(BaseContracts.ModelOutput output) {
        this.output = (BionimeContracts.ModelOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }
    @Override
    public void  createDbandInsert(final DbHelper dbHelper){ //取得政府開放平台資料
        String url = "http://opendata.epa.gov.tw/api/v1/AQI?%24skip=0&%24top=1000&%24format=json";
        JsonArrayRequest objectRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        dbHelper.execSQL(); // 創建Table
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                long res =  dbHelper.insert(obj.getString("SiteName"), obj.getString("County"),
                                        obj.getString("AQI"),obj.getString("Status"),obj.getString("Pollutant"));
                                if (res == -1){
                                    output.didShowwarringview("資料庫寫入失敗,請稍後在試一次 !");
                                    Log.d(TAG, "Db insert is failed");
                                    break;
                                }
                            }
                            output.didUpdateListview();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            output.didShowwarringview("Json載入失敗 ! " + e.toString());
                            Log.e(TAG, "error Json+ " + e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.didShowwarringview("網頁載入失敗 ! " + error.toString());
                Log.e(TAG, "error + " + error.toString());
            }
        }
        );
        Volley.newRequestQueue(context).add(objectRequest);
    }

    @Override
    public boolean isConnetedWifi() {
        if(context != null){
            ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetInfo==null){
                return false;
            }
            return activeNetInfo.isAvailable();
        }
        return true;
    }

}
