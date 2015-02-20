package com.utterlyswagger.petshop;

import com.utterlyswagger.petshop.path.PathAssertions;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

public abstract class TestPetShopSwagger {

    @Test
    public void definesSwaggerVersionAndBasePath() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                PathAssertions.stringInPath(is("2.0"), "swagger"),
                PathAssertions.stringInPath(is("basePath"), "/v2")));
    }

    protected abstract Map<String, Object> getSwagger() throws Exception;
}
