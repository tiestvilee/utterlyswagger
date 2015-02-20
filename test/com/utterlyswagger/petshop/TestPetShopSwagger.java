package com.utterlyswagger.petshop;

import com.utterlyswagger.petshop.path.PathAssertions;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class TestPetShopSwagger {

    @Test
    public void definesSwaggerVersion() throws Exception {
        assertThat(
            getSwagger(),
            PathAssertions.stringInPath(is("2.0"), "swagger"));
    }

    protected abstract Map<String, Object> getSwagger() throws Exception;
}
