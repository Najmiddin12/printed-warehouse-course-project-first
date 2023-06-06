package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.Parameter;
import org.example.criteria.parameters.impl.EditionParameter;
import org.example.entity.Range;
import org.example.entity.Book;

public class EditionParameterConverter extends AbstractParameterConverter<Book> {

    public EditionParameterConverter() {
        super("edition");
    }

    @Override
    protected Parameter<Book> internalConvert(String request) {
        var value = request.split("-", 2);
        Range<Integer> range = value.length == 1
                ? new Range<>(Integer.parseInt(value[0]), Integer.parseInt(value[0]))
                : new Range<>(Integer.parseInt(value[0]), Integer.parseInt(value[1]));
        return new EditionParameter(range);
    }
}

