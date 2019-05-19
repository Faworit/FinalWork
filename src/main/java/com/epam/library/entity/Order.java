package com.epam.library.entity;

import java.util.Date;
import java.util.Objects;

public class Order extends Entity{

    private String bookTitle;
    private String state;
    private Date orderDate;
    private Date acceptedDate;
    private Date returnDate;
    private Date actuallyReturn;
    private User reader;
    private User librarian;

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public User getLibrarian() {
        return librarian;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getActuallyReturn() {
        return actuallyReturn;
    }

    public void setActuallyReturn(Date actuallyReturn) {
        this.actuallyReturn = actuallyReturn;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", bookTitle='" + bookTitle + '\'' +
                ", state='" + state + '\'' +
                ", orderDate=" + orderDate +
                ", returnDate=" + returnDate +
                ", actuallyReturn=" + actuallyReturn +
                ", reader=" + reader +
                ", librarian=" + librarian +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(bookTitle, order.bookTitle) &&
                Objects.equals(state, order.state) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(acceptedDate, order.acceptedDate) &&
                Objects.equals(returnDate, order.returnDate) &&
                Objects.equals(actuallyReturn, order.actuallyReturn) &&
                Objects.equals(reader, order.reader) &&
                Objects.equals(librarian, order.librarian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookTitle, state, orderDate, acceptedDate, returnDate, actuallyReturn, reader, librarian);
    }
}
