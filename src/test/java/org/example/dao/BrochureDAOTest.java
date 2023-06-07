package org.example.dao;

import org.example.criteria.impl.BrochureSearchCriteria;
import org.example.criteria.parameters.Parameter;
import org.example.dao.impl.BrochureDAO;
import org.example.entity.Brochure;
import org.example.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BrochureDAOTest {

  @Test
  void shouldFindAll() {
    // arrange
    BrochureDAO dao = new BrochureDAO("brochures.test.csv");
    Brochure[] expected = new Brochure[]{
            new Brochure()
                    .setTitle("Target Catalog")
                    .setPublisher("Shaks Media")
                    .setTargetAudience("Designers")
                    .setPaperType("Bond")
                    .setSize("A3")
                    .setPrice(800),
            new Brochure()
                    .setTitle("Media Programm")
                    .setPublisher("Meraki Marketing")
                    .setTargetAudience("Blogers")
                    .setPaperType("Tissue")
                    .setSize("A4")
                    .setPrice(900),
            new Brochure()
                    .setTitle("Food Menu")
                    .setPublisher("La Cometa")
                    .setTargetAudience("Singers")
                    .setPaperType("Vellum")
                    .setSize("A4")
                    .setPrice(700),
    };
  }
  @Test
  void shouldFindNone() {
    // arrange
    BrochureDAO dao = new BrochureDAO("brochures.test.csv");

    // action
    Iterable<Brochure> iterable = dao.find(new BrochureSearchCriteria().add(Parameter.none()));

    // assert
    assertNotNull(iterable);
    Brochure[] brochures = toArray(iterable, Brochure[]::new);
    assertArrayEquals(new Brochure[0], brochures);
  }

  private <A extends Product<A>> A[] toArray(Iterable<A> iterable, IntFunction<A[]> arrayGen) {
    ArrayList<A> list = new ArrayList<>();
    iterable.forEach(list::add);
    A[] arr = list.toArray(arrayGen);
    Arrays.sort(arr, Comparator.comparingInt(Product::getPrice));
    return arr;
  }

}