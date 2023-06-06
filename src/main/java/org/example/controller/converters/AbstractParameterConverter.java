package org.example.controller.converters;

import org.example.criteria.parameters.InvalidParameterArguments;
import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;

public abstract class AbstractParameterConverter<A  extends Product<A>> implements ParameterConverter<A> {

    private final String parameterName;

    protected AbstractParameterConverter(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public Parameter<A> convert(String request) throws ParameterConversionException {
        try {
            return internalConvert(request);
        }
        catch (NumberFormatException e) {
            throw new ParameterConversionException("The passed value is not a number: " + request);
        }
        catch (InvalidParameterArguments e) {
            throw new ParameterConversionException("Request '" + request + "' is invalid because " + e.getMessage());
        }
    }

    protected abstract Parameter<A> internalConvert(String request) throws ParameterConversionException;

    @Override
    public String parameterName() {
        return parameterName;
    }
}

