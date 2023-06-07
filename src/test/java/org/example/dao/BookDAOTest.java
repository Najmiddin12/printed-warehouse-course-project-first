package org.example.dao;

import org.example.dao.impl.BookDAO;
import org.example.entity.Book;
import org.example.criteria.impl.BookSearchCriteria;
import org.example.criteria.parameters.Parameter;

import org.example.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;




class BookDAOTest {

  @Test
  void shouldFindAll() {
    // arrange
    BookDAO dao = new BookDAO("books.test.csv");
    Book[] expected = new Book[]{
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
                    .setPrice(1400),
    };
  }
  @Test
  void shouldFindNone() {
    // arrange
    BookDAO dao = new BookDAO("books.test.csv");

    // action
    Iterable<Book> iterable = dao.find(new BookSearchCriteria().add(Parameter.none()));

    // assert
    assertNotNull(iterable);
    Book[] books = toArray(iterable, Book[]::new);
    assertArrayEquals(new Book[0], books);
  }

  private <A extends Product<A>> A[] toArray(Iterable<A> iterable, IntFunction<A[]> arrayGen) {
    ArrayList<A> list = new ArrayList<>();
    iterable.forEach(list::add);
    A[] arr = list.toArray(arrayGen);
    Arrays.sort(arr, Comparator.comparingInt(Product::getPrice));
    return arr;
  }

}