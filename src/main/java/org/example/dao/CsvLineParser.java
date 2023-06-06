package org.example.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CsvLineParser<T> {

    private final Supplier<T> generator;
    private final List<Extractor<?, T>> extractors;

    public CsvLineParser(Supplier<T> generator, List<Extractor<?, T>> extractors) {
        this.generator = Objects.requireNonNull(generator);
        this.extractors = Objects.requireNonNull(extractors);
    }

    public T parse(String[] csvLine) {
        if (csvLine.length != extractors.size()) {
            throw new IllegalArgumentException("CSV line is invalid. Expected " + extractors.size()
                    + ", but was " + csvLine.length + ". " + Arrays.toString(csvLine));
        }
        var value = generator.get();
        for (int i = 0; i < csvLine.length; ++i) {
            Extractor<?, T> extractor = extractors.get(i);
            extractor.extractAndSet(value, unquote(csvLine[i]));
        }
        return value;
    }

    private static String unquote(String column) {
        return column.replaceAll("^\"|\"$", "");
    }

    public static record Extractor<V, T>(BiConsumer<T, V> setter, Function<String, V> converter) {

        void extractAndSet(T value, String column) {
            setter.accept(value, converter.apply(column));
        }

        public static <T> Extractor<String, T> forString(BiConsumer<T, String> setter) {
            return new Extractor<>(setter, Function.identity());
        }

        public static <T> Extractor<Integer, T> forInt(BiConsumer<T, Integer> setter) {
            return new Extractor<>(setter, Integer::parseInt);
        }
    }
}
