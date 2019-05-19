package com.epam.library.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book extends Entity {

    private String title;
    private String ISBN;
    private int quantity;
    private int languageID;
    private List<Author> authors = new ArrayList<>();
    private List<Genre> geners = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGeners() {
        return geners;
    }

    public void setGeners(List<Genre> geners) {
        this.geners = geners;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", quantity=" + quantity +
                ", languageID=" + languageID +
                ", authors=" + authors +
                ", geners=" + geners +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return quantity == book.quantity &&
                languageID == book.languageID &&
                Objects.equals(title, book.title) &&
                Objects.equals(ISBN, book.ISBN) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(geners, book.geners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, ISBN, quantity, languageID, authors, geners);
    }
}
