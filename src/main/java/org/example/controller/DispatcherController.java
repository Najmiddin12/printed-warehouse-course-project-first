package org.example.controller;

import org.example.controller.concrete.ConcreteController;
import org.example.controller.concrete.ConcreteController.Response;
import org.example.controller.io.FinishStatus;
import org.example.controller.io.IOHolder;
import org.example.entity.Product;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class DispatcherController implements Closeable {

    private final IOHolder io;
    private final Map<String, ConcreteController<? extends Product<?>>> controllers;

    public DispatcherController(IOHolder io, Map<String, ConcreteController<?>> controllers) {
        this.io = Objects.requireNonNull(io);
        this.controllers = Objects.requireNonNull(controllers);
    }

    public void listen() throws IOException {
        io.getReader()
                .stopOnStatus(FinishStatus.END_OF_INPUT)
                .doAction(request -> {
                    ConcreteController<? extends Product<?>> controller = controllers.get(request.toLowerCase());
                    if (controller == null) {
                        io.error("Unsupported appliance type: " + request);
                        return FinishStatus.CONTINUE;
                    }
                    Response<? extends Product<?>> response = controller.acceptRequest(io);
                    switch (response.status()) {
                        case END_OF_INPUT -> {
                            return FinishStatus.END_OF_INPUT;
                        }
                        case SEARCH -> response.products().forEach(io::print);
                        case COUNT -> io.print(response.products().size());
                    }
                    return FinishStatus.CONTINUE;
                })
                .read();
    }

    @Override
    public void close() throws IOException {
        io.close();
    }
}

