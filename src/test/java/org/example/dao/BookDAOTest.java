package org.example.dao;

import org.example.dao.impl.BookDAO;
import org.example.entity.Book;
import org.junit.jupiter.api.Test;

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
                    .setFormat("Pdf"),
            new Book()
                    .setTitle("10x Rule")
                    .setPublisher("Bartelsmann AG")
                    .setAuthor("John Mcwell")
                    .setEdition(2)
                    .setFormat("word"),
            new Book()
                    .setTitle("Death in the Clouds")
                    .setPublisher("Penguin Book")
                    .setAuthor("Agatha Christie")
                    .setEdition(3)
                    .setFormat("Pdf"),
    };
  }
}