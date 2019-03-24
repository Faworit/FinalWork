package com.epam.library.service;

import com.epam.library.dataBase.AuthorDAO;
import com.epam.library.dataBase.BookDAO;
import com.epam.library.dataBase.BookGenreDAO;
import com.epam.library.validator.BookValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBookService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        boolean isAvailableBook;
        int idBook;
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String titleRU = request.getParameter("titleRU");
        String titleENG = request.getParameter("titleENG");
        String ISBN = request.getParameter("ISBN");
        String genre = request.getParameter("genre");
        String authorFromJSP = request.getParameter("author");
        System.out.println(authorFromJSP);
        List<Integer> idAuthors = getIDAuthors(authorFromJSP);
        BookDAO bookDAO = new BookDAO();
        BookGenreDAO bookGenreDAO = new BookGenreDAO();
        idBook = bookDAO.getLastIdBook()+1;
        List<String> genres = parseGenreRequest(genre);
        isAvailableBook = BookValidator.checkBook(ISBN);
        if(isAvailableBook){
            request.setAttribute("alreadyExists", "This book is already exists");
            dispatcher = request.getRequestDispatcher("addBook.jsp");
            dispatcher.forward(request, response);
        } else{
            bookDAO.addBook(idBook, 1, ISBN, quantity, titleRU);
            bookDAO.addBook(idBook, 2, ISBN, quantity, titleENG);
            bookGenreDAO.addData(idBook,1,genres);
            bookGenreDAO.addData(idBook,2,genres);
            for (int i = 0; i < idAuthors.size(); i++) {
                bookDAO.setIntoBook2Author(idBook, idAuthors.get(i));
            }
            request.setAttribute("information", "new book added successfully");
            dispatcher = request.getRequestDispatcher("information.jsp");
            dispatcher.forward(request, response);
        }
    }

    private List<String> parseGenreRequest(String genre){
        List<String> genres;
        String[] words = genre.replaceAll("[^а-яА-Яa-zA-Z]", " ").replaceAll("\\s+", " ").split("\\s");
        genres = new ArrayList<>(Arrays.asList(words));
        return genres;
    }

    private List<Integer> getIDAuthors(String authorFromJSP){
        List<String> namesSurnames = parseAuthorRequest(authorFromJSP);
        List<Integer> idAuthors = new ArrayList<>();
        AuthorDAO authorDAO = new AuthorDAO();
        for (int i = 0; i < namesSurnames.size(); i++) {
            System.out.println(namesSurnames.get(i));
            System.out.println("sefse");
            int idAuthor;
            String name;
            String surname;
            Matcher matcher = Pattern.compile("[^\\s{1}]+").matcher(namesSurnames.get(i));
            matcher.find();
            name = matcher.group();
            matcher.find();
            surname = matcher.group();
            idAuthor = authorDAO.getID(name, surname);
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
