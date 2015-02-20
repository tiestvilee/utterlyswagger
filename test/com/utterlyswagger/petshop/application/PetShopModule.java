package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.modules.ResourcesModule;

import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

public class PetShopModule implements ResourcesModule {
    @Override
    public Resources addResources(Resources resources) throws Exception {
        return resources.add(annotatedClass(PetShopResource.class));
    }
}
