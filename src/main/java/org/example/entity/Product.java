package org.example.entity;

import java.util.Objects;

public abstract class Product<SELF extends Product<SELF>> {

    private String title;
    private String publisher;
    private int price;

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

    public int getPrice() { return  price; }

    public SELF setPrice(int price) {
        this.price = price;
        return (SELF) this;
    }

    @Override
    public String toString() {
        return "Product{" + commonFields() + '}';
    }

    protected String commonFields() {
        return "title=" + title
            + ", publisher=" + publisher
            + ", price=" + price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product<?> product = (Product<?>) o;
        return price == product.price &&
                Objects.equals(title, product.title) &&
                Objects.equals(publisher, product.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPublisher(), getPrice());
    }


}
