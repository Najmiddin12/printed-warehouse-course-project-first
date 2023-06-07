package org.example.config;

import org.example.controller.DispatcherController;
import org.example.controller.concrete.BookController;
import org.example.controller.concrete.MagazineController;
import org.example.controller.concrete.BrochureController;
import org.example.controller.io.IOHolder;
import org.example.service.ProductService;
import java.util.List;
import java.util.Map;

public enum ControllerFactory {
    INSTANCE;

    public DispatcherController dispatcher(ProductService productService) {
        return new DispatcherController(
                IOHolder.system(),
                Map.of(
                        "books", new BookController(productService, List.of(
                                RawConverters.TITLE.generic(),
                                RawConverters.PUBLISHER.generic(),
                                RawConverters.PRICE.generic(),
                                RawConverters.EDITION.generic(),
                                RawConverters.FORMAT.generic()
                        )),
                        "magazines", new MagazineController(productService, List.of(
                                RawConverters.TITLE.generic(),
                                RawConverters.PUBLISHER.generic(),
                                RawConverters.PRICE.generic(),
                                RawConverters.NUMBER_OF_PAGES.generic()
                        )),
                        "brochures", new BrochureController(productService, List.of(
                                RawConverters.TITLE.generic(),
                                RawConverters.PUBLISHER.generic(),
                                RawConverters.PRICE.generic(),
                                RawConverters.PAPER_TYPE.generic(),
                                RawConverters.SIZE.generic()
                        ))
                )
        );
    }
}
