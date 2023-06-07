package org.example.criteria;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;

public interface SearchCriteria<A extends Product<A>> {

    public Class<A> getProductType();

    public <P extends Parameter<A>> SearchCriteria<A> add(P parameter);

    public boolean test(A product);

    // you may add your own code here

}
