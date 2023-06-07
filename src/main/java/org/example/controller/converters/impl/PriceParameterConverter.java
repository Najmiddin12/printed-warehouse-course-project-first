package org.example.controller.converters.impl;

import org.example.controller.converters.AbstractParameterConverter;
import org.example.criteria.parameters.Parameter;
import org.example.criteria.parameters.impl.PriceParameter;
import org.example.entity.Product;
import org.example.entity.Range;

public class PriceParameterConverter<A extends Product<A>> extends AbstractParameterConverter<A> {

  public PriceParameterConverter() {
    super("price");
  }


  @Override
  protected Parameter<A> internalConvert(String request) {
    var value = request.split("-", 2);
    Range<Integer> range = value.length == 1
            ? new Range<>(Integer.parseInt(value[0]), Integer.parseInt(value[0]))
            : new Range<>(Integer.parseInt(value[0]), Integer.parseInt(value[1]));
    return new PriceParameter<>(range);
  }

}
