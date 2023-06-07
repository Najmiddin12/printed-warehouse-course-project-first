package org.example.dao;


import org.example.criteria.impl.MagazineSearchCriteria;
import org.example.criteria.parameters.Parameter;
import org.example.dao.impl.MagazineDAO;
import org.example.entity.Magazine;
import org.example.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class MagazineDAOTest {

  @Test
  void shouldFindAll() {
    // arrange
    MagazineDAO dao = new MagazineDAO("magazines.test.csv");
    Magazine[] expected = new Magazine[]{
            new Magazine()
                    .setTitle("Bloomberg")
                    .setPublisher("Bloomberg Businessweek")
                    .setIssueDate("August 2021")
                    .setNumberOfPages(145)
                    .setPrice(700),
            new Magazine()
                    .setTitle("The New York")
                    .setPublisher("Cosmopolitan Co")
                    .setIssueDate("December 2022")
                    .setNumberOfPages(127)
                    .setPrice(600),
            new Magazine()
                    .setTitle("GQ")
                    .setPublisher("Vogue Corporation")
                    .setIssueDate("July 2023")
                    .setNumberOfPages(154)
                    .setPrice(700),
    };
  }
  @Test
  void shouldFindNone() {
    // arrange
    MagazineDAO dao = new MagazineDAO("magazines.test.csv");

    // action
    Iterable<Magazine> iterable = dao.find(new MagazineSearchCriteria().add(Parameter.none()));

    // assert
    assertNotNull(iterable);
    Magazine[] magazines = toArray(iterable, Magazine[]::new);
    assertArrayEquals(new Magazine[0], magazines);
  }

  private <A extends Product<A>> A[] toArray(Iterable<A> iterable, IntFunction<A[]> arrayGen) {
    ArrayList<A> list = new ArrayList<>();
    iterable.forEach(list::add);
    A[] arr = list.toArray(arrayGen);
    Arrays.sort(arr, Comparator.comparingInt(Product::getPrice));
    return arr;
  }

}