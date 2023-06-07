package org.example.dao;

import org.example.config.DaoFactoryImpl;
import org.example.dao.impl.BookDAO;
import org.example.entity.Product;
import org.example.entity.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOFactoryTest {

  @Test
  void shouldReturnNullWhenUnknownClassIsPassed() {
    class DummyProduct extends Product<DummyProduct> {}
    ProductDAO<DummyProduct> dao = DaoFactoryImpl.INSTANCE.getProductDAO(DummyProduct.class);
    assertNull(dao);
  }
  
  @Test
  void shouldReturnOvenDao() {
    ProductDAO<Book> dao = DaoFactoryImpl.INSTANCE.getProductDAO(Book.class);
    assertNotNull(dao);
    assertTrue(dao instanceof BookDAO);
  }
}