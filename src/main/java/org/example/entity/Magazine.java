package org.example.entity;

import java.util.Objects;

public class Magazine extends Product<Magazine>{
    private String issueDate;
    private int numberOfPages;


    public String getIssueDate() { return issueDate; }

    public Magazine setIssueDate(String issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public int getNumberOfPages() { return numberOfPages; }

    public Magazine setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    @Override
    public String toString() {
        return "Magazine{" + String.join(", ",
                commonFields(),
                "issueDate=" + issueDate,
                "numberOfPages=" + numberOfPages
        ) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return numberOfPages == magazine.numberOfPages && Objects.equals(issueDate, magazine.issueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), issueDate, numberOfPages);
    }
}

