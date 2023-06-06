package org.example.controller.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

public class IOHolder implements Closeable {

    private final InputStream in;
    private final PrintStream out;
    private final PrintStream err;
    private BufferedReader openedIn;

    public IOHolder(InputStream in, OutputStream out, OutputStream err) {
        this.in = Objects.requireNonNull(in);
        this.out = new PrintStream(Objects.requireNonNull(out), true);
        this.err = new PrintStream(Objects.requireNonNull(err), true);
    }

    public static IOHolder system() {
        return new IOHolder(System.in, System.out, System.err);
    }

    void setOpenedIn(BufferedReader reader) {
        this.openedIn = reader;
    }

    public InputReader.Builder getReader() {
        if (openedIn != null) {
            return InputReader.from(this, openedIn);
        }
        else {
            return InputReader.from(this, in);
        }
    }

    public void print(Object msg) {
        out.println(msg);
    }

    public void error(Object msg) {
        err.println(msg);
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        err.close();
    }
}
