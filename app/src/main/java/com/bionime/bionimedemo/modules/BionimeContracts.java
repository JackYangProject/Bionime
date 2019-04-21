package com.bionime.bionimedemo.modules;

import com.bionime.bionimedemo.BaseContracts;
import com.bionime.bionimedemo.sqlite.DbHelper;
import com.bionime.bionimedemo.tools.Myaqi;

import java.util.List;

public interface BionimeContracts extends BaseContracts {
    interface View extends BaseContracts.View {
        void updateListview(List<Myaqi> myaqis);
        void showDialog(int data,String site,String cty,String aqi,String sts,String pol);
        void showToast(String txt);
        void showWarringView(String warr);
        void hideWarringView();
    }

    interface Presenter extends BaseContracts.Presenter {
        void didOnItemClick(int position);
        void didDeleteData(int data);
        void didRefresh();
    }

    interface Model extends BaseContracts.Model {
        void createDbandInsert(DbHelper dbHelper);
        boolean isConnetedWifi();
    }

    interface ModelOutput extends BaseContracts.ModelOutput {
        void didUpdateListview();
        void didShowwarringview(String war);
    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}
