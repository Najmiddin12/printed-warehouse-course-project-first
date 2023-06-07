package org.example.controller.concrete;

import org.example.config.RawConverters;
import org.example.controller.TestEnvironmentSetupUtil;
import org.example.controller.TestEnvironmentSetupUtil.TestEnvironment;
import org.example.controller.io.FinishStatus;
import org.example.dao.impl.MagazineDAO;
import org.example.entity.Magazine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagazineControllerTest {

  private ConcreteController<Magazine> magazineController;

  private TestEnvironment env;
  private final List<Magazine> existingMagazines = List.of(
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
                  .setPrice(700)
  );

  @BeforeEach
  void setUp() {
    env = new TestEnvironmentSetupUtil()
        .addProduct(Magazine.class, new MagazineDAO("magazines.test.csv"), service -> new MagazineController(service, List.of(
            RawConverters.PRICE.generic()
        )))
        .setUp();
    magazineController = env.getController(Magazine.class);
  }

  @Test
  void shouldFindAllMagazines() throws Exception {
    var holder = env.createIOHolder("search");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
  }

  @Test
  void shouldFindOnlyPriceMagazines() throws Exception {
    var holder = env.createIOHolder("price = 700", "search");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    var expected = List.of(
        existingMagazines.get(0),
        existingMagazines.get(2)
    );
  }

  @Test
  void shouldCountOnlyPriceMagazines() throws Exception {
    var holder = env.createIOHolder("price = 700", "count");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.COUNT, response.status());
    var expected = List.of(
        existingMagazines.get(0),
        existingMagazines.get(2)
    );
  }

  @Test
  void shouldReturnCancelStatusWithoutProducts() throws Exception {
    var holder = env.createIOHolder("price = 700", "cancel");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.CANCEL, response.status());
    assertTrue(response.products().isEmpty());
  }

  @Test
  void shouldReturnEndOfInputStatusWithoutProducts() throws Exception {
    var holder = env.createIOHolder("price = 700", "quit");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.END_OF_INPUT, response.status());
    assertTrue(response.products().isEmpty());
  }


  @Test
  void shouldWriteToErrIfParameterIsUnsupported() throws Exception {
    var holder = env.createIOHolder("pages = 700", "search");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    assertEquals("Invalid parameter: pages", env.err().toString().trim());
  }

  @Test
  void shouldWriteToErrIfParameterValueIsInvalid() throws Exception {
    var holder = env.createIOHolder("price = August 2021", "search");
    var response = magazineController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    assertEquals("The passed value is not a number: August 2021", env.err().toString().trim());
  }


  private static <T> void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
    assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
  }
}