package org.example.controller.converters;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;

public interface ParameterConverter<A extends Product<A>> {

    Parameter<A> convert(String request) throws ParameterConversionException;

    String parameterName();
}
