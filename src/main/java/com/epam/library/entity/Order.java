package com.epam.library.entity;

import java.util.Date;

public class Order {

    private int orderID;
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

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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
                "orderID=" + orderID +
                ", bookTitle='" + bookTitle + '\'' +
                ", state='" + state + '\'' +
                ", orderDate=" + orderDate +
                ", returnDate=" + returnDate +
                ", actuallyReturn=" + actuallyReturn +
                ", reader=" + reader +
                ", librarian=" + librarian +
                '}';
    }
}
