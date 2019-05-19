package com.epam.library.factory;

import com.epam.library.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorFactory {

    public static Author createAuthor(ResultSet resultSet) throws SQLException {
        int IDAuthor = resultSet.getInt("ID_AUTHOR");
        String surName = resultSet.getString("surname");
        String name = resultSet.getString("name");
        Author author = new Author(name, surName);
        author.setId(IDAuthor);
        return author;
    }
}
