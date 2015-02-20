package com.utterlyswagger.petshop.path;

import com.googlecode.totallylazy.Unchecked;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Collection;

import static com.googlecode.totallylazy.Sequences.sequence;

public class PathAssertions {
    public static Matcher<? super Object> stringInPath(Matcher<? super String> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static Matcher<? super Object> integerInPath(Matcher<? super Integer> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static Matcher<? super Object> listInPath(Matcher<? super Collection<Object>> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static class PathMatcher<T> extends FeatureMatcher<Object, T> {
        private final java.lang.Object[] path;

        public PathMatcher(Matcher<? super T> subMatcher, Object... path) {
            super(subMatcher,
                "path: " + pathToString(path),
                ", " + sequence(path).last());

            this.path = path;
        }

        @Override
        protected T featureValueOf(Object actual) {
            return
                SafePath.objectAt(actual, path)
                    .map(Unchecked::<T>cast)
                    .getOrElse(() -> {
                        throw new AssertionError("Didn't find value at path: " + pathToString(path));
                    });
        }

        public static String pathToString(Object[] path) {return "[/" + sequence(path).toString("/") + "]";}

    }
}
