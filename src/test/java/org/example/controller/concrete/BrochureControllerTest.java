package org.example.controller.concrete;

import org.example.config.RawConverters;
import org.example.controller.TestEnvironmentSetupUtil;
import org.example.controller.TestEnvironmentSetupUtil.TestEnvironment;
import org.example.controller.io.FinishStatus;
import org.example.dao.impl.BrochureDAO;
import org.example.entity.Brochure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrochureControllerTest {

  private ConcreteController<Brochure> brochureController;

  private TestEnvironment env;
  private final List<Brochure> existingBrochures = List.of(
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
                  .setPrice(700)
  );

  @BeforeEach
  void setUp() {
    env = new TestEnvironmentSetupUtil()
        .addProduct(Brochure.class, new BrochureDAO("brochures.test.csv"), service -> new BrochureController(service, List.of(
            RawConverters.PRICE.generic(),
            RawConverters.SIZE.generic()
        )))
        .setUp();
    brochureController = env.getController(Brochure.class);
  }

  @Test
  void shouldFindAllBrochures() throws Exception {
    var holder = env.createIOHolder("search");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
  }
  
  @Test
  void shouldFindOnlySizeBrochures() throws Exception {
    var holder = env.createIOHolder("size = A4", "search");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    var expected = List.of(
        existingBrochures.get(0),
        existingBrochures.get(2)
    );
  }
  
  @Test
  void shouldCountOnlySizeBrochures() throws Exception {
    var holder = env.createIOHolder("size = A4", "count");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.COUNT, response.status());
    var expected = List.of(
        existingBrochures.get(0),
        existingBrochures.get(2)
    );
  }
  
  @Test
  void shouldReturnCancelStatusWithoutProducts() throws Exception {
    var holder = env.createIOHolder("size = A4", "cancel");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.CANCEL, response.status());
    assertTrue(response.products().isEmpty());
  }
  
  @Test
  void shouldReturnEndOfInputStatusWithoutProducts() throws Exception {
    var holder = env.createIOHolder("size = A4", "quit");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.END_OF_INPUT, response.status());
    assertTrue(response.products().isEmpty());
  }

  
  @Test
  void shouldWriteToErrIfParameterIsUnsupported() throws Exception {
    var holder = env.createIOHolder("type = A4", "search");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    assertEquals("Invalid parameter: type", env.err().toString().trim());
  }
  
  @Test
  void shouldWriteToErrIfParameterValueIsInvalid() throws Exception {
    var holder = env.createIOHolder("price = A4", "search");
    var response = brochureController.acceptRequest(holder);
    assertEquals(FinishStatus.SEARCH, response.status());
    assertEquals("The passed value is not a number: A4", env.err().toString().trim());
  }

  private static <T> void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
    assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
  }
}