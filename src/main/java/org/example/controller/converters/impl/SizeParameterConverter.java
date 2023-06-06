package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.Parameter;
import org.example.criteria.parameters.impl.SizeParameter;
import org.example.entity.Brochure;

public class SizeParameterConverter extends AbstractParameterConverter<Brochure> {

    public SizeParameterConverter() {
        super("size");
    }

    @Override
    protected Parameter<Brochure> internalConvert(String request) {
        return new SizeParameter(request);
    }
}


