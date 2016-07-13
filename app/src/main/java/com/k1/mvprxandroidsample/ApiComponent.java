package com.k1.mvprxandroidsample;

import dagger.Component;

/**
 * Created by K1 on 7/14/16.
 */
@CustomScope
@Component(modules = ApiModule.class, dependencies = NetworkComponent.class)
public interface ApiComponent {

    void inject(EnthusiasticActivity activity);
}
