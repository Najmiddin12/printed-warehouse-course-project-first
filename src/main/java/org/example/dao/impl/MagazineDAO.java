package org.example.dao.impl;

import static org.example.dao.CsvLineParser.Extractor.forInt;
import static org.example.dao.CsvLineParser.Extractor.forString;

import org.example.dao.AbstractDao;
import org.example.dao.CsvLineParser;
import org.example.entity.Product;
import org.example.entity.Magazine;
import java.util.List;

public class MagazineDAO extends AbstractDao<Magazine> {

    public MagazineDAO(String path) {
        super(path, new CsvLineParser<>(Magazine::new, List.of(
                forString(Product::setTitle),
                forString(Product::setPublisher),
                forString(Magazine::setIssueDate),
                forInt(Magazine::setNumberOfPages),
                forInt(Product::setPrice)
        )));
    }
}


