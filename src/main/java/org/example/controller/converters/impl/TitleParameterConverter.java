package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.impl.TitleParameter;
import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;

public class TitleParameterConverter<A extends Product<A>> extends AbstractParameterConverter<A> {

    public TitleParameterConverter() {
        super("title");
    }


    @Override
    protected Parameter<A> internalConvert(String request) {
        return new TitleParameter<>(request);
    }
}
