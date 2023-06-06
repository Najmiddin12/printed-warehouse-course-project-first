package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.Parameter;
import org.example.criteria.parameters.impl.NumberOfPagesParameter;
import org.example.entity.Range;
import org.example.entity.Magazine;

public class NumberOfPagesParameterConverter extends AbstractParameterConverter<Magazine> {

    public NumberOfPagesParameterConverter() {
        super("numberOfPages");
    }

    @Override
    protected Parameter<Magazine> internalConvert(String request) {
        var value = request.split("-", 2);
        Range<Integer> range = value.length == 1
                ? new Range<>(Integer.parseInt(value[0]), Integer.parseInt(value[0]))
                : new Range<>(Integer.parseInt(value[0]), Integer.parseInt(value[1]));
        return new NumberOfPagesParameter(range);
    }
}




