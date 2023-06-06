package org.example.config;

import org.example.controller.converters.ParameterConverter;
import org.example.controller.converters.impl.TitleParameterConverter;
import org.example.controller.converters.impl.PublisherParameterConverter;
import org.example.controller.converters.impl.EditionParameterConverter;
import org.example.controller.converters.impl.FormatParameterConverter;
import org.example.controller.converters.impl.NumberOfPagesParameterConverter;
import org.example.controller.converters.impl.PaperTypeParameterConverter;
import org.example.controller.converters.impl.SizeParameterConverter;
import org.example.entity.Product;

@SuppressWarnings("rawtypes")
public enum RawConverters {
    TITLE(new TitleParameterConverter()),
    PUBLISHER(new PublisherParameterConverter()),
    EDITION(new EditionParameterConverter()),
    FORMAT(new FormatParameterConverter()),
    NUMBER_OF_PAGES(new NumberOfPagesParameterConverter()),
    PAPER_TYPE(new PaperTypeParameterConverter()),
    SIZE(new SizeParameterConverter());

    private final ParameterConverter converter;

    RawConverters(ParameterConverter converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    public <A extends Product<A>> ParameterConverter<A> generic() {
        return (ParameterConverter<A>) converter;
    }
}
