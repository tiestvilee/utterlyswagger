package com.utterlyswagger.petshop.path;

import com.googlecode.totallylazy.Unchecked;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Map;

import static com.googlecode.totallylazy.Sequences.sequence;

public class PathAssertions {
    public static Matcher<? super Object> objectInPath(Matcher<? super Object> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static Matcher<? super Object> stringInPath(Matcher<? super String> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static Matcher<? super Object> integerInPath(Matcher<? super Integer> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static Matcher<? super Object> listInPath(Matcher<? super Collection<Object>> valueMatcher, Object... path) { return new PathMatcher<>(valueMatcher, path); }

    public static Matcher<? super Object> mapInPathKeys(Matcher<? super Collection<String>> valueMatcher, Object... path) {
        return new KeyPathMatcher(valueMatcher, path);
    }

    public static Matcher<? super Object> nothingInPath(Object... path) { return new NoPathMatcher(path); }

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

    public static class KeyPathMatcher extends FeatureMatcher<Object, Collection<String>> {
        private final java.lang.Object[] path;

        public KeyPathMatcher(Matcher<? super Collection<String>> subMatcher, Object... path) {
            super(subMatcher,
                "path: " + pathToString(path),
                ", " + sequence(path).last());

            this.path = path;
        }

        @Override
        protected Collection<String> featureValueOf(Object actual) {
            return
                SafePath.objectAt(actual, path)
                    .map(Unchecked::<Map>cast)
                    .map(Map::keySet)
                    .map(Unchecked::<Collection<String>>cast)
                    .getOrElse(() -> {
                        throw new AssertionError("Didn't find value at path: " + pathToString(path));
                    });
        }

        public static String pathToString(Object[] path) {return "[/" + sequence(path).toString("/") + "]";}

    }

    public static class NoPathMatcher extends BaseMatcher<Object> {
        private final java.lang.Object[] path;

        public NoPathMatcher(Object... path) {
            this.path = path;
        }

        public static String pathToString(Object[] path) {return "[/" + sequence(path).toString("/") + "]";}

        @Override
        public boolean matches(Object o) {
            return SafePath.objectAt(o, path).isEmpty();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("path " + pathToString(path) + " is empty");
        }

        @Override
        public void describeMismatch(Object item, Description description) {
            description.appendText("yet it had a value of ").appendValue(SafePath.objectAt(item, path).get());
        }
    }
}
