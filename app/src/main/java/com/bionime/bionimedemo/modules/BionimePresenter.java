package com.bionime.bionimedemo.modules;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bionime.bionimedemo.BaseContracts;
import com.bionime.bionimedemo.BasedbContracts;
import com.bionime.bionimedemo.sqlite.DbHelper;
import com.bionime.bionimedemo.tools.Myaqi;

import java.util.ArrayList;
import java.util.List;

public class BionimePresenter implements BionimeContracts.Presenter,BionimeContracts.ModelOutput, BasedbContracts {
    private BionimeContracts.View view;
    private BionimeContracts.Model model;
    private BionimeContracts.Wireframe router;
    private String TAG = "Log-> Presenter";
    private List<Myaqi> myaqis = new ArrayList<>();
    private DbHelper dbHelper;
    private Context context;
    BionimePresenter(Context context){
        this.context = context;
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (BionimeContracts.View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Model model) {
        this.model =(BionimeContracts.Model) model;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (BionimeContracts.Wireframe)router;
    }

    @Override
    public void decompose() {
        model.decompose();
        view.decompose();
        model = null;
        view = null;
        router = null;
    }
    @Override
    public void onCreate() {
        dbHelper = new DbHelper(context);
        didonCreate();
    }
    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
    }

    @Override
    public void didUpdateListview() {
        Cursor cursor = dbHelper.query();
        if (cursor.moveToFirst()) {
            do {
                String sitenamelist = cursor.getString(cursor.getColumnIndex(siteName));
                String countylist = cursor.getString(cursor.getColumnIndex(county));
                int aqilist = cursor.getInt(cursor.getColumnIndex(aqi));
                String statuslist = cursor.getString(cursor.getColumnIndex(status));
                String pollutantlist = cursor.getString(cursor.getColumnIndex(pollutant));
                myaqis.add(new Myaqi(sitenamelist, countylist, String.valueOf(aqilist), statuslist, pollutantlist));
            } while (cursor.moveToNext());
            view.updateListview(myaqis);
            cursor.close();
            Log.d(TAG, "myaqis  => " + myaqis.size());
        }
    }
    @Override
    public void didShowwarringview(String war) {
        view.showWarringView(war);
    }

    @Override
    public void didOnItemClick(int position) {
        view.showDialog(position,myaqis.get(position).getSiteName(),myaqis.get(position).getCounty()
                ,myaqis.get(position).getAqi(),myaqis.get(position).getStatus(),myaqis.get(position).getPollutant());
    }

    @Override
    public void didDeleteData(int data) {
        dbHelper.delete(myaqis.get(data).getSiteName());
        myaqis.remove(data);
        view.updateListview(myaqis);
        view.showToast("刪除成功");
        Log.d(TAG,"delete  Data");
    }

    @Override
    public void didRefresh() {
        didonCreate();
    }

    private void didonCreate(){
        view.hideWarringView();
        if (!model.isConnetedWifi()){
            view.showWarringView("網路未連結,請檢察網路");
            view.showToast("網路未連結");
            return;
        }
        if (!dbHelper.tabIsExist(TableName)){
            model.createDbandInsert(dbHelper);
            Log.d(TAG,"Not have Table Create " );
        }else {
            didUpdateListview();
            Log.d(TAG,"Already have Table" );
        }
    }

}
