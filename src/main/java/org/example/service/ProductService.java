package org.example.service;

import org.example.entity.Product;
import org.example.criteria.SearchCriteria;
import java.util.Collection;

public interface ProductService {

    <A extends Product<A>> Collection<A> find(SearchCriteria<A> criteria);

}
