package org.example.criteria;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCriteria<A extends Product<A>> implements SearchCriteria<A> {

    protected final Map<Class<?>, Parameter<A>> parameters = new HashMap<>();

    @Override
    public <F extends Parameter<A>> SearchCriteria<A> add(F parameter) {
        parameters.put(parameter.getClass(), parameter);
        return this;
    }

    @Override
    public boolean test(A product) {
        return parameters.values().stream()
                .map(p -> p.test(product))
                .reduce(Boolean::logicalAnd)
                .orElse(true);
    }
}

