package org.example.controller.io;

public enum FinishStatus {
    END_OF_INPUT("quit", false),
    CANCEL("cancel", false),
    SEARCH("search", true),
    COUNT("count", true),
    CONTINUE("continue", false);

    private final String term;
    private final boolean success;

    FinishStatus(String term, boolean success) {
        this.term = term;
        this.success = success;
    }

    public boolean testRequest(String request) {
        return term.equalsIgnoreCase(request);
    }

    public boolean isSuccess() {
        return success;
    }
}

