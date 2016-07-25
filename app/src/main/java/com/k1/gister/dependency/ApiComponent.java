package com.k1.gister.dependency;

import com.k1.gister.ListActivity;

import dagger.Component;

/**
 * Created by K1 on 7/14/16.
 */
@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {

    void inject(ListActivity activity);
}
