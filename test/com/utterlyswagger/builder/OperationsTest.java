package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Option;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Type;

import static com.utterlyswagger.builder.Operations.typeFor;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OperationsTest {

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