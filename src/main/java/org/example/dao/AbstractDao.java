package org.example.dao;

import org.example.criteria.SearchCriteria;
import org.example.entity.Product;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class AbstractDao<A extends Product<A>> implements ProductDAO<A> {

    private final Path csvPath;
    private final CsvLineParser<A> parser;

    protected AbstractDao(String path, CsvLineParser<A> parser) {
        try {
            var resource = getClass().getClassLoader().getResource(path);
            if (resource == null) {
                throw new IllegalArgumentException("No CSV file is found: " + path);
            }
            csvPath = Path.of(resource.toURI());
            this.parser = Objects.requireNonNull(parser);
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<A> find(SearchCriteria<A> criteria) {
        try (var in = Files.newBufferedReader(csvPath)){
            in.readLine(); // headers
            String line;
            var products = new ArrayList<A>();
            while ((line = in.readLine()) != null) {
                String[] csvLine = line.split(";");
                var product = parser.parse(csvLine);
                if (criteria.test(product)) products.add(product);
            }
            return products;
        }
        catch (IOException e) {
            return List.of();
        }
    }
}