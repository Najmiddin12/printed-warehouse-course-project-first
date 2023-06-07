package org.example.controller.concrete;

import org.example.config.RawConverters;
import org.example.controller.TestEnvironmentSetupUtil;
import org.example.controller.TestEnvironmentSetupUtil.TestEnvironment;
import org.example.controller.io.FinishStatus;
import org.example.dao.impl.BookDAO;
import org.example.entity.Book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookControllerTest {

  private ConcreteController<Book> bookController;

  private TestEnvironment env;
  private final List<Book> existingBooks = List.of(
          new Book()
                  .setTitle("The Great Game")
                  .setPublisher("Shcolastic")
                  .setAuthor("Crott Gitzgerald")
                  .setEdition(3)
                  .setFormat("Pdf")
                  .setPrice(1300),
          new Book()
                  .setTitle("10x Rule")
                  .setPublisher("Bartelsmann AG")
                  .setAuthor("John Mcwell")
                  .setEdition(2)
                  .setFormat("word")
                  .setPrice(1200),
          new Book()
                  .setTitle("Death in the Clouds")
                  .setPublisher("Penguin Book")
                  .setAuthor("Agatha Christie")
                  .setEdition(3)
                  .setFormat("Pdf")
                  .setPrice(1400)
  );

  @BeforeEach
  void setUp() {
    env = new TestEnvironmentSetupUtil()
        .addProduct(Book.class, new BookDAO("books.test.csv"), service -> new BookController(service, List.of(
                RawConverters.PRICE.generic(),
                RawConverters.FORMAT.generic()
        )))
        .setUp();
    bookController = env.getController(Book.class);
  }

  @Test
  void shouldFindAllBooks() throws Exception {
    var holder = env.createIOHolder("search");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
  }
  
  @Test
  void shouldFindOnlyPdfBooks() throws Exception {
    var holder = env.createIOHolder("format = Pdf", "search");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    var expected = List.of(
        existingBooks.get(0),
        existingBooks.get(2)
    );
  }
  
  @Test
  void shouldCountOnlyPdfBooks() throws Exception {
    var holder = env.createIOHolder("format = Pdf", "count");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.COUNT, response.status());
    var expected = List.of(
        existingBooks.get(0),
        existingBooks.get(2)
    );
  }
  
  @Test
  void shouldReturnCancelStatusWithoutProducts() throws Exception {
    var holder = env.createIOHolder("format = Pdf", "cancel");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.CANCEL, response.status());
    assertTrue(response.products().isEmpty());
  }
  
  @Test
  void shouldReturnEndOfInputStatusWithoutProducts() throws Exception {
    var holder = env.createIOHolder("format = Pdf", "quit");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.END_OF_INPUT, response.status());
    assertTrue(response.products().isEmpty());
  }

  
  @Test
  void shouldWriteToErrIfParameterIsUnsupported() throws Exception {
    var holder = env.createIOHolder("type = Pdf", "search");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    assertEquals("Invalid parameter: type", env.err().toString().trim());
  }
  
  @Test
  void shouldWriteToErrIfParameterValueIsInvalid() throws Exception {
    var holder = env.createIOHolder("price = Pdf", "search");
    var response = bookController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    assertEquals("The passed value is not a number: Pdf", env.err().toString().trim());
  }

  private static <T> void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
    assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
  }
}