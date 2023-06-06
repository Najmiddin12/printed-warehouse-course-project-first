package org.example.controller.concrete;

import org.example.controller.converters.ParameterConverter;
import org.example.criteria.impl.BookSearchCriteria;
import org.example.criteria.SearchCriteria;
import org.example.entity.Book;
import org.example.service.ProductService;
import java.util.List;

public class BookController extends ConcreteController<Book> {

    public BookController(ProductService productService, List<ParameterConverter<Book>> converters) {
        super(productService, converters);
    }

    @Override
    protected SearchCriteria<Book> createCriteria() {
        return new BookSearchCriteria();
    }
}

