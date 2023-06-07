package org.example.controller;


import org.example.config.RawConverters;
import org.example.controller.TestEnvironmentSetupUtil.TestEnvironment;
import org.example.controller.concrete.ConcreteController;
import org.example.controller.concrete.BookController;
import org.example.controller.concrete.MagazineController;
import org.example.dao.impl.BookDAO;
import org.example.dao.impl.MagazineDAO;
import org.example.entity.Book;
import org.example.entity.Magazine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DispatcherControllerTest {

  private TestEnvironment env;
  private Map<String, ConcreteController<?>> controllers;
  
  @BeforeEach
  void setUp() {
    env = new TestEnvironmentSetupUtil()
        .addProduct(Book.class, new BookDAO("books.test.csv"), service -> new BookController(service, List.of(
            RawConverters.PRICE.generic()
        )))
        .addProduct(Magazine.class, new MagazineDAO("magazines.test.csv"), service -> new MagazineController(service, List.of(
            RawConverters.PRICE.generic()
        )))
        .setUp();
    controllers = Map.of(
        "books", env.getController(Book.class),
        "magazines", env.getController(Magazine.class)
    );
  }
  
  @Test
  void shouldSearchSuccessfully() throws Exception {
    var dispatcher = dispatcher(
        "books",
        "search",
        "quit"
    );
    dispatcher.listen();
    String expectedOut = """
                Book{title=The Great Gatsby, publisher=F. Scott Fitzgerald, price=1200, author=Scribner, edition=2, format=Paperback}
                Book{title=To Kill a Mockingbird, publisher=Harper Lee, price=1500, author=J. B. Lippincott & Co, edition=2, format=Hardcover}
                Book{title=1984, publisher=George Orwell, price=1100, author=Secker & Warburg, edition=1, format=Paperback}
            """;
    assertEquals("", env.err().toString());
  }
  
  @Test
  void shouldNotPrintIfCanceled() throws Exception {
    var dispatcher = dispatcher(
        "books",
        "cancel",
        "quit"
    );
    dispatcher.listen();
    assertEquals("", env.out().toString());
    assertEquals("", env.err().toString());
  }
  
  @Test
  void shouldSupportChangingConcreteController() throws Exception {
    var dispatcher = dispatcher(
        "magazines",
        "cancel",
        "books",
        "search",
        "quit"
    );
    dispatcher.listen();
    String expectedOut = """
                Book{title=The Great Gatsby, publisher=F. Scott Fitzgerald, price=1200, author=Scribner, edition=2, format=Paperback}
                Book{title=To Kill a Mockingbird, publisher=Harper Lee, price=1500, author=J. B. Lippincott & Co, edition=2, format=Hardcover}
                Book{title=1984, publisher=George Orwell, price=1100, author=Secker & Warburg, edition=1, format=Paperback}
            """;
    assertEquals("", env.err().toString());
  }
  
  @Test
  void shouldQuitFromInsideConcreteController() throws Exception {
    var dispatcher = dispatcher(
        "magazines",
        "quit"
    );
    dispatcher.listen();
    assertEquals("", env.out().toString());
    assertEquals("", env.err().toString());
  }
  
  @Test
  void shouldWriteToErrIfUnknownPathIsUsed() throws Exception {
    var dispatcher = dispatcher(
        "poster",
        "quit"
    );
    dispatcher.listen();
    assertEquals("", env.out().toString());
  }
  
  private DispatcherController dispatcher(String... input) {
    return new DispatcherController(env.createIOHolder(input), controllers);
  }
}