package com.bionime.bionimedemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bionime.bionimedemo.modules.BionimeRouter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment root = BionimeRouter.setupModule(getApplicationContext());
        getFragmentManager().beginTransaction().add(R.id.framelayout_view,root).commit();
    }
}
