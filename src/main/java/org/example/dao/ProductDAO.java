package org.example.dao;

import org.example.entity.Product;
import org.example.criteria.SearchCriteria;
import java.util.Collection;

public interface ProductDAO<A extends Product<A>> {

    Collection<A> find(SearchCriteria<A> criteria);

}
