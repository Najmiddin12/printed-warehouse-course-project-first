package org.example;

import org.example.config.ControllerFactory;
import org.example.service.ProductService;
import org.example.config.ServiceFactory;

public class Main {

    public static void main(String[] args) {
        ProductService service = ServiceFactory.INSTANCE.getApplianceService();
        try (var dispatcher = ControllerFactory.INSTANCE.dispatcher(service)) {
            dispatcher.listen();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
