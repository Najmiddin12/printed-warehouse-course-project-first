package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.impl.PublisherParameter;
import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;

public class PublisherParameterConverter<A extends Product<A>> extends AbstractParameterConverter<A> {

    public PublisherParameterConverter() {
        super("publisher");
    }


    @Override
    protected Parameter<A> internalConvert(String request) {
        return new PublisherParameter<>(request);
    }
}

