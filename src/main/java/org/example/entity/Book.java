package org.example.entity;

import java.util.Objects;

public class Book extends Product<Book>{
    private String author;
    private int edition;
    private String format;


    public String getAuthor() { return author; }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getEdition() { return edition; }

    public Book setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    public String getFormat() { return format; }

    public Book setFormat(String format) {
        this.format = format;
        return this;
    }

    @Override
    public String toString() {
        return "Book{" + String.join(", ",
                commonFields(),
                "author=" + author,
                "edition=" + edition,
                "format=" + format
                ) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return format == book.format && Objects.equals(author, book.author) && Objects.equals(edition, book.edition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, edition, format);
    }
}
