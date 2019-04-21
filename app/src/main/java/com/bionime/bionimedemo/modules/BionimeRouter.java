package com.bionime.bionimedemo.modules;

import android.app.Fragment;
import android.content.Context;


public class BionimeRouter implements BionimeContracts.Wireframe {
    private Context context;
    private Fragment view;
    private BionimeContracts.Presenter presenter;

    private BionimeRouter(Context context){
        this.context = context;
    }
    public static BionimeFragment setupModule(Context context){
        BionimeFragment view = new BionimeFragment();
        BionimeModules modules = new BionimeModules(context);
        BionimePresenter presenter = new BionimePresenter(context);
        BionimeRouter router = new BionimeRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(modules);

        router.view = view;
        router.presenter = presenter;
        modules.setupPresenter(presenter);

        return view;
    }

}
