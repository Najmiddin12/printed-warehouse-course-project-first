package org.example.entity;

import java.util.Objects;

public abstract class Product<SELF extends Product<SELF>> {

    private String title;
    private String publisher;

    public String getTitle() { return  title; }

    public SELF setTitle(String title) {
        this.title = title;
        return (SELF) this;
    }

    public String getPublisher() { return  publisher; }

    public SELF setPublisher(String publisher) {
        this.publisher = publisher;
        return (SELF) this;
    }

    @Override
    public String toString() {
        return "Product{" + commonFields() + '}';
    }

    protected String commonFields() {
        return "title=" + title
            + ", publisher=" + publisher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product<?> product = (Product<?>) o;
        return publisher == product.publisher &&
                Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPublisher());
    }


}
