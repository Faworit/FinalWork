package com.epam.library.service;

import com.epam.library.dataBase.impl.AuthorDAOImpl;
import com.epam.library.dataBase.impl.BookDAOImpl;
import com.epam.library.dataBase.impl.BookGenreDAOImpl;
import com.epam.library.entity.Book;
import com.epam.library.validator.BookValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBookService implements Service {

    RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String genre = request.getParameter("genre");
        List<String> genres = parseGenreRequest(genre);

        String ISBN = request.getParameter("ISBN");
        boolean isAvailableBook = BookValidator.checkBook(ISBN);
        if(isAvailableBook){
            request.setAttribute("alreadyExists", "This book is already exists");
            dispatcher = request.getRequestDispatcher("addBook.jsp");
            dispatcher.forward(request, response);
        } else{
            BookDAOImpl bookDAO = new BookDAOImpl();
            int idBook = bookDAO.getLastIdBook()+1;
            List<Book> books = createBook(request, ISBN, idBook);
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                bookDAO.create(book);
            }
            BookGenreDAOImpl bookGenreDAO = new BookGenreDAOImpl();
            bookGenreDAO.addData(idBook,1,genres);
            bookGenreDAO.addData(idBook,2,genres);
            String authorFromJSP = request.getParameter("author");
            List<Integer> idAuthors = getIDAuthors(authorFromJSP);
            for (int i = 0; i < idAuthors.size(); i++) {
                bookDAO.setIntoBook2Author(idBook, idAuthors.get(i));
            }
            request.setAttribute("information", "new book added successfully");
            dispatcher = request.getRequestDispatcher("information.jsp");
            dispatcher.forward(request, response);
        }
    }

    private List<Book> createBook(HttpServletRequest request, String ISBN, int idBook){
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        List<Book> books = new ArrayList<>();
        HashMap<String, Integer> titles = getTitles(request);
        for(Map.Entry<String, Integer> item : titles.entrySet()){
            Book book = new Book();
            book.setId(idBook);
            book.setTitle(item.getKey());
            book.setLanguageID(item.getValue());
            book.setISBN(ISBN);
            book.setQuantity(quantity);
            books.add(book);
        }
        return books;
    }

    private HashMap<String, Integer> getTitles(HttpServletRequest request){
        HashMap<String, Integer> titles = new HashMap<>();
        titles.put(request.getParameter("titleRU"), 1);
        titles.put(request.getParameter("titleENG"),2);
        return titles;
    }

    private List<String> parseGenreRequest(String genre){
        List<String> genres;
        String[] words = genre.replaceAll("[^а-яА-Яa-zA-Z]", " ").replaceAll("\\s+", " ").split("\\s");
        genres = new ArrayList<>(Arrays.asList(words));
        return genres;
    }

    private List<Integer> getIDAuthors(String authorFromJSP) throws SQLException {
        List<String> namesSurnames = parseAuthorRequest(authorFromJSP);
        List<Integer> idAuthors = new ArrayList<>();
        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        for (int i = 0; i < namesSurnames.size(); i++) {
            Matcher matcher = Pattern.compile("[^\\s{1}]+").matcher(namesSurnames.get(i));
            matcher.find();
            String name = matcher.group();
            matcher.find();
            String surname = matcher.group();
            int idAuthor = authorDAO.getID(name, surname);
            idAuthors.add(idAuthor);
        }
        return  idAuthors;
    }

    private List<String> parseAuthorRequest(String author){
        List<String> namesSurnames = new ArrayList<>();
        Matcher matcher = Pattern.compile("[^,]+").matcher(author.trim());
        while (matcher.find()){
            String nameSurname = matcher.group();
            namesSurnames.add(nameSurname);
        }
        return namesSurnames;
    }
}
