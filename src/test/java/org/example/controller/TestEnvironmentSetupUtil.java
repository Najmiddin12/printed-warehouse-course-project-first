package org.example.controller;

import org.example.controller.concrete.ConcreteController;
import org.example.controller.io.IOHolder;
import org.example.dao.ProductDAO;
import org.example.dao.DaoFactory;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestEnvironmentSetupUtil {

  private final Set<TestDescriptorEntry<?>> entries = new HashSet<>();

  public <A extends Product<A>> TestEnvironmentSetupUtil addProduct(Class<A> type, ProductDAO<A> dao,
      Function<ProductService, ConcreteController<A>> controller) {
    this.entries.add(new TestDescriptorEntry<>(type, dao, controller));
    return this;
  }

  public TestEnvironment setUp() {
    var out = new ByteArrayOutputStream();
    var err = new ByteArrayOutputStream();
    var daoFactory = new DaoFactory() {

      @Override
      @SuppressWarnings("unchecked")
      public <A extends Product<A>> ProductDAO<A> getProductDAO(Class<A> productClass) {
        return ((ProductDAO<A>) entries.stream()
            .filter(entry -> entry.type.equals(productClass))
            .findFirst()
            .orElseThrow()
            .dao());
      }
    };
    var service = new ProductServiceImpl(daoFactory);
    var controllers = entries.stream()
        .collect(Collectors.<TestDescriptorEntry<?>, Class<?>, ConcreteController<?>>toMap(
            TestDescriptorEntry::type,
            entry -> entry.controller().apply(service)
        ));
    return new TestEnvironment(out, err, service, controllers);
  }

  public static record TestEnvironment(ByteArrayOutputStream out, ByteArrayOutputStream err,
                                       ProductService productService,
                                       Map<Class<?>, ConcreteController<?>> controllers) {

    @SuppressWarnings("unchecked")
    public <A extends Product<A>> ConcreteController<A> getController(Class<A> type) {
      return ((ConcreteController<A>) controllers.get(type));
    }

    public IOHolder createIOHolder(String... input) {
      return new IOHolder(new ByteArrayInputStream(String.join(System.lineSeparator(), input)
          .getBytes(StandardCharsets.UTF_8)), out, err);
    }
  }

  private static record TestDescriptorEntry<A extends Product<A>>(
      Class<A> type, ProductDAO<A> dao, Function<ProductService, ConcreteController<A>> controller) {

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      return obj instanceof TestDescriptorEntry<?> entry && entry.type.equals(this.type);
    }

    @Override
    public int hashCode() {
      return type.hashCode();
    }
  }
}
