package com.utterlyswagger.builder;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PathMatcherTest {
    @Test
    public void matchesPathWithNoRegex() throws Exception {
        assertThat(PathMatcher.operationPath("john/mary/{id}"), equalTo("/john/mary/{id}"));
    }
    @Test
    public void matchesPathWithRegex() throws Exception {
        assertThat(PathMatcher.operationPath("john/mary/{id:(.*)?}"), equalTo("/john/mary/{id}"));
    }

    @Test
    public void matchesPathWithMultipleParamsWithNoRegex() throws Exception {
        assertThat(PathMatcher.operationPath("john/mary/{id}/{age}"), equalTo("/john/mary/{id}/{age}"));
    }

    @Test
    public void matchesPathWithMultipleParamsWithMultipleRegex() throws Exception {
        assertThat(PathMatcher.operationPath("john/mary/{id:([a-z]+)?}/{age:(.*)?}"), equalTo("/john/mary/{id}/{age}"));
    }

    @Test
    public void matchesPathWithMultipleParamsWithMixedRegex() throws Exception {
        assertThat(PathMatcher.operationPath("john/mary/{id:([a-z]+)?}/{age}"), equalTo("/john/mary/{id}/{age}"));
    }

}