package org.example.dao.impl;

import static org.example.dao.CsvLineParser.Extractor.forString;

import org.example.dao.AbstractDao;
import org.example.dao.CsvLineParser;
import org.example.entity.Product;
import org.example.entity.Brochure;
import java.util.List;

public class BrochureDAO extends AbstractDao<Brochure> {

    public BrochureDAO(String path) {
        super(path, new CsvLineParser<>(Brochure::new, List.of(
                forString(Product::setTitle),
                forString(Product::setPublisher),
                forString(Brochure::setTargetAudience),
                forString(Brochure::setPaperType),
                forString(Brochure::setSize)
        )));
    }
}

