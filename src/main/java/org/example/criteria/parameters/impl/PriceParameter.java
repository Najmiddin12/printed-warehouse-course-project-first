package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;
import org.example.entity.Range;
import org.example.criteria.parameters.InvalidParameterArguments;


public record PriceParameter<A extends Product<A>>(Range<Integer> price) implements Parameter<A> {

  public PriceParameter {
    if (price.from() < 0L) {
      throw new InvalidParameterArguments("Price can't be less than 0, but was " + price.from());
    }
  }
  @Override
  public boolean test(A product) {
    return price.isIn(product.getPrice());
  }
}
