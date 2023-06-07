package org.example;

import org.example.config.ControllerFactory;
import org.example.service.ProductService;
import org.example.config.ServiceFactory;

public class Main {

    public static void main(String[] args) {
        System.out.println("""
                Project name: Printed warehouse.
                Developer: Sultanov Najmiddin.
                Products: books, magazines, brochures.
                Commands: search, count, cancel, quit.
                """);
        ProductService service = ServiceFactory.INSTANCE.getProductService();
        try (var dispatcher = ControllerFactory.INSTANCE.dispatcher(service)) {
            dispatcher.listen();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
