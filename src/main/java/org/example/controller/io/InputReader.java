package org.example.controller.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class InputReader {

    private final BufferedReader in;
    private final Set<FinishStatus> stopStatuses;
    private final Action action;

    private InputReader(IOHolder holder, InputStream in, Set<FinishStatus> stopStatuses, Action action) {
        this(new BufferedReader(new InputStreamReader(in)), stopStatuses, action);
        holder.setOpenedIn(this.in);
    }

    private InputReader(BufferedReader in, Set<FinishStatus> stopStatuses, Action action) {
        this.in = in;
        this.stopStatuses = stopStatuses;
        this.action = action;
    }

    public FinishStatus read() throws IOException {
        while (true) {
            var request = in.readLine();
            for (FinishStatus status : stopStatuses) {
                if (status.testRequest(request)) {
                    return status;
                }
                if (request == null) {
                    return FinishStatus.END_OF_INPUT;
                }
            }
            var status = action.apply(request);
            if (status == FinishStatus.CONTINUE) continue;
            return status;
        }
    }

    static Builder from(IOHolder holder, InputStream in) {
        return new Builder(holder, in);
    }

    static Builder from(IOHolder holder, BufferedReader in) {
        return new Builder(holder, in);
    }

    @FunctionalInterface
    public static interface Action {
        FinishStatus apply(String request) throws IOException;
    }

    public static class Builder {

        private final IOHolder holder;
        private final InputStream in;
        private final BufferedReader reader;
        private final Set<FinishStatus> stopStatuses = new HashSet<>();

        private Builder(IOHolder holder, InputStream in) {
            this.holder = holder;
            this.in = in;
            this.reader = null;
        }

        private Builder(IOHolder holder, BufferedReader reader) {
            this.holder = holder;
            this.in = null;
            this.reader = reader;
        }

        public Builder stopOnStatus(FinishStatus status) {
            stopStatuses.add(status);
            return this;
        }

        public InputReader doAction(Action action) {
            if (in != null) {
                return new InputReader(holder, in, stopStatuses, action);
            }
            else {
                return new InputReader(reader, stopStatuses, action);
            }
        }
    }
}

