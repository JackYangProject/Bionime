package com.bionime.bionimedemo.modules;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bionime.bionimedemo.BaseCollectionAdapterDelegate;
import com.bionime.bionimedemo.BaseContracts;
import com.bionime.bionimedemo.R;
import com.bionime.bionimedemo.tools.CommonDialog;
import com.bionime.bionimedemo.tools.Listadpter;
import com.bionime.bionimedemo.tools.Myaqi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.List;


public class BionimeFragment extends Fragment implements BionimeContracts.View, BaseCollectionAdapterDelegate {
    private ViewGroup view;
    private BionimeContracts.Presenter presenter;
    private Listadpter listadpter;
    private String TAG = "Log-> View";
    private RelativeLayout warring_view;
    private TextView warring_txt;

    public BionimeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = (ViewGroup) inflater.inflate(R.layout.fragment_bionime, container, false);
        Thread thread = new Thread(runnable);
        thread.start();
        onRecyclerView();
        onWarringView();
        presenter.onCreate();
        return view;
    }

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (BionimeContracts.Presenter) presenter;
    }

    @Override
    public void decompose() {
        presenter = null;
        view = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.didOnItemClick(position);
    }

    @Override
    public void updateListview(List<Myaqi> myaqis) {
        listadpter.setMyaqis(myaqis);
        listadpter.notifyDataSetChanged();
    }

    @Override
    public void showDialog(final int data,String site, String cty, String aqi, String sts, String pol) {
        final CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle("刪除資料");
        dialog.setImageResId(android.R.drawable.ic_dialog_info);
        dialog.setSitename(site).setCounty(cty).setAqi(aqi).setStatus(sts).setPollutant(pol)
                .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                presenter.didDeleteData(data);
                dialog.dismiss();
            }
            @Override
            public void onNegtiveClick() {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showToast(final String txt) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),txt,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void showWarringView(final String warr) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                warring_view.setVisibility(View.VISIBLE);
                warring_txt.setText(warr);
            }
        });
    }

    @Override
    public void hideWarringView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                warring_view.setVisibility(View.GONE);
            }
        });
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://tw.appledaily.com/index/dailyquote");
                Document doc = Jsoup.parse(url,10000);
                Elements title = doc.select("article[class]");
                final Elements elements = title.get(0).select("p");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = view.findViewById(R.id.title_txt);
                        textView.setText(elements.text());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                showToast("標題網頁載入失敗 ! " + e.getMessage());
                Log.d(TAG,"Elements  is failed=> " + e.getMessage());
            }
        }
    };

    private void onRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        listadpter = new Listadpter(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                ,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(listadpter);
        listadpter.setupDelegate(this);
    }

    private void onWarringView(){
        warring_view = view.findViewById(R.id.warring_view);
        warring_txt = view.findViewById(R.id.warring_txt);
        view.findViewById(R.id.warring_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(runnable);
                thread.start();
                presenter.didRefresh();
            }
        });
    }
}
