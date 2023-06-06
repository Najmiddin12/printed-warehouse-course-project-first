package org.example.controller.concrete;

import org.example.controller.io.FinishStatus;
import org.example.controller.io.IOHolder;
import org.example.controller.converters.ParameterConversionException;
import org.example.controller.converters.ParameterConverter;
import org.example.criteria.SearchCriteria;
import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;
import org.example.service.ProductService;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ConcreteController<A extends Product<A>> {

    protected final ProductService productService;
    protected final Map<String, ParameterConverter<A>> parameterConverters;

    public static record Response<A extends Product<A>>(FinishStatus status, Collection<A> products) {}

    protected ConcreteController(ProductService productService,
                                 Collection<ParameterConverter<A>> parameterConverters) {
        this.productService = Objects.requireNonNull(productService);
        this.parameterConverters = Objects.requireNonNull(parameterConverters).stream()
                .collect(Collectors.toMap(
                        ParameterConverter::parameterName,
                        Function.identity()
                ));
    }

    protected abstract SearchCriteria<A> createCriteria();

    public Response<A> acceptRequest(IOHolder io) throws IOException {
        SearchCriteria<A> criteria = createCriteria();
        var status = io.getReader()
                .stopOnStatus(FinishStatus.END_OF_INPUT)
                .stopOnStatus(FinishStatus.SEARCH)
                .stopOnStatus(FinishStatus.CANCEL)
                .stopOnStatus(FinishStatus.COUNT)
                .doAction(request -> {
                    try {
                        criteria.add(toParameter(request));
                    }
                    catch (IllegalArgumentException e) {
                        io.error("Invalid parameter: " + e.getMessage());
                    }
                    catch (ParameterConversionException e) {
                        io.error(e.getMessage());
                    }
                    return FinishStatus.CONTINUE;
                })
                .read();
        return new Response<>(status, status.isSuccess() ? productService.find(criteria) : List.of());
    }

    private Parameter<A> toParameter(String parameter) throws ParameterConversionException {
        var desc = parameter.split("=");
        var converter = parameterConverters.get(desc[0].strip().toLowerCase());
        if (converter == null) {
            throw new IllegalArgumentException(desc[0].strip());
        }
        return converter.convert(desc[1].strip());
    }
}
