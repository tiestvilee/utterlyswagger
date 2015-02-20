package com.utterlyswagger.petshop.path;

import com.googlecode.totallylazy.*;

import java.util.List;
import java.util.Map;

import static com.googlecode.totallylazy.Option.none;
import static com.googlecode.totallylazy.Option.option;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;

public class SafePath {
    public static Option<Map<String, Object>> mapAt(Object source, Object... path) { return objectAt(source, path).map(map -> (Map<String, Object>) map); }

    public static Sequence<Object> sequenceAt(Object source, Object... path) {
        return objectAt(source, path)
            .map(list -> sequence(asList(list).getOrElse(() -> {
                throw new ClassCastException(list.getClass() + " is not a sequence");
            })))
            .getOrElse(sequence());
    }

    public static Option<String> stringAt(Object source, Object... path) { return objectAt(source, path).map(string -> (String) string); }

    public static Option<Number> numberAt(Object source, Object... path) { return objectAt(source, path).map(number -> (Number) number); }

    public static Option<Object> objectAt(Object source, Object... path) {
        if (path.length == 0) { return option(source); }

        Object head = path[0];
        if (head instanceof String) {
            return asMap(source)
                .map(map -> objectAt(map.get(head), Arrays.tail(path)))
                .getOrElse(none());
        } else if (head instanceof Integer) {
            return asList(source)
                .map(list ->
                    list.size() > (Integer) head
                        ? objectAt(list.get((Integer) head), Arrays.tail(path))
                        : none())
                .getOrElse(none());
        }
        throw new IllegalArgumentException(format("Expected String or Integer but got %s", head.getClass()));
    }

    @SuppressWarnings("unchecked")
    private static Option<Map<String, Object>> asMap(Object source) {
        return source instanceof Map ? option((Map<String, Object>) source) : none();
    }

    @SuppressWarnings("unchecked")
    private static Option<List<Object>> asList(Object source) {
        return source instanceof List ? option((List<Object>) source) : none();
    }

}
