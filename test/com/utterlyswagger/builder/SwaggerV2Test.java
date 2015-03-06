package com.utterlyswagger.builder;

import com.utterlyswagger.SwaggerInfo;
import org.junit.Test;

import static com.utterlyswagger.petshop.path.PathAssertions.nothingInPath;
import static com.utterlyswagger.petshop.path.PathAssertions.stringInPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

public class SwaggerV2Test {

    @Test
    public void removes_unset_fields() throws Exception {
        assertThat(
            SwaggerV2.swaggerInfo(
                new SwaggerInfo("title-text", "api-version")
                    .description("description-text")),
            allOf(
                stringInPath(is("description-text"), "description"),
                nothingInPath("termsOfService")
            ));
    }

}