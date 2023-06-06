package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.impl.FormatParameter;
import org.example.criteria.parameters.Parameter;
import org.example.entity.Book;

public class FormatParameterConverter extends AbstractParameterConverter<Book> {

    public FormatParameterConverter() {
        super("format");
    }

    @Override
    protected Parameter<Book> internalConvert(String request) {
        return new FormatParameter(request);
    }
}

