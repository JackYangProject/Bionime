package com.bionime.bionimedemo;

import java.io.Serializable;

public interface BaseContracts {

    interface View extends Serializable {
        void setupPresenter(Presenter presenter);
        void decompose();
    }

    interface Presenter extends Serializable {
        void onCreate();
        void onPause();
        void onResume();
        void onDestroy();

        void setupView(View view);
        void setupInteractor(Model model);
        void setupWireframe(Wireframe router);
        void decompose();
    }

    interface Model extends Serializable {
        void setupPresenter(ModelOutput output);
        void decompose();
    }

    interface ModelOutput extends Serializable {

    }

    interface Wireframe extends Serializable {
    }

}
