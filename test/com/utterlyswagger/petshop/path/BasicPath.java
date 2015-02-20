package com.utterlyswagger.petshop.path;

import com.googlecode.totallylazy.Arrays;
import com.googlecode.totallylazy.Sequence;

import java.util.List;
import java.util.Map;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.Unchecked.cast;
import static java.lang.String.format;

public class BasicPath {
    public static Map<String, Object> mapAt(Object source, Object... path) { return cast(objectAt(source, path)); }

    public static Sequence<Object> sequenceAt(Object source, Object... path) { return sequence(asList(objectAt(source, path))); }

    public static String stringAt(Object source, Object... path) { return cast(objectAt(source, path)); }

    public static Number numberAt(Object source, Object... path) { return cast(objectAt(source, path)); }

    public static Object objectAt(Object source, Object... path) {
        if (path.length == 0) { return source; }

        Object head = path[0];
        if (head instanceof String) {
            return objectAt(asMap(source).get(head), Arrays.tail(path));
        } else if (head instanceof Integer) {
            return objectAt(asList(source).get((Integer) head), Arrays.tail(path));
        }
        throw new IllegalArgumentException(format("Expected String or Integer but got %s", head.getClass()));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> asMap(Object source) { return (Map<String, Object>) source; }

    @SuppressWarnings("unchecked")
    private static List<Object> asList(Object source) {return (List<Object>) source;}

}
