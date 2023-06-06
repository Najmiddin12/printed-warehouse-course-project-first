package org.example.dao.impl;

import static org.example.dao.CsvLineParser.Extractor.forInt;
import static org.example.dao.CsvLineParser.Extractor.forString;

import org.example.dao.AbstractDao;
import org.example.dao.CsvLineParser;
import org.example.entity.Product;
import org.example.entity.Book;
import java.util.List;

public class BookDAO extends AbstractDao<Book> {

    public BookDAO(String path) {
        super(path, new CsvLineParser<>(Book::new, List.of(
                forString(Product::setTitle),
                forString(Product::setPublisher),
                forString(Book::setAuthor),
                forInt(Book::setEdition),
                forString(Book::setFormat)
        )));
    }
}

