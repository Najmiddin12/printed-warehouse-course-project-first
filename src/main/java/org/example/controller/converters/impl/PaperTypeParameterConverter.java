package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.impl.PaperTypeParameter;
import org.example.criteria.parameters.Parameter;
import org.example.entity.Brochure;

public class PaperTypeParameterConverter extends AbstractParameterConverter<Brochure> {

    public PaperTypeParameterConverter() {
        super("paperType");
    }

    @Override
    protected Parameter<Brochure> internalConvert(String request) {
        return new PaperTypeParameter(request);
    }
}


