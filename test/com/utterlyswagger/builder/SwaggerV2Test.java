package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Option;
import com.utterlyswagger.SwaggerInfo;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Type;

import static com.utterlyswagger.builder.SwaggerV2.typeFor;
import static com.utterlyswagger.petshop.path.PathAssertions.nothingInPath;
import static com.utterlyswagger.petshop.path.PathAssertions.stringInPath;
import static java.lang.String.format;
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

    @Test
    public void converts_parameter_types() throws Exception {
        assertThat(typeFor(String.class), is("string"));
        assertThat(typeFor(Integer.class), is("integer"));
        assertThat(typeFor(Long.class), is("integer"));
        assertThat(typeFor(Byte.class), is("integer"));
        assertThat(typeFor(Short.class), is("integer"));
        assertThat(typeFor(Double.class), is("number"));
        assertThat(typeFor(Float.class), is("number"));
        assertThat(typeFor(Boolean.class), is("boolean"));
        assertThat(typeFor(Color.class), is("unknown: java.awt.Color"));
    }

    @Test
    public void removes_option_from_parameter_types() throws Exception {
        assertThat(typeFor(optionOfString), is("string"));
    }

    private final Type optionOfString = new Type() {
        @Override
        public String getTypeName() {
            return format("%s<%s>", Option.class.getCanonicalName(), String.class.getCanonicalName());
        }
    };

}