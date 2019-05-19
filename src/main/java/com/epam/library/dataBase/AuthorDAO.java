package com.epam.library.dataBase;

import com.epam.library.entity.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDAO extends CommonDAO<Author>  {
    int getID(String name, String surname) throws SQLException;
    List<Author> getAll() throws SQLException;
    List<Author> getAuthor(int IDBook, int language) throws SQLException;
    boolean isExist(String name, String surname) throws SQLException;
    void editAuthor(String name, String surname, int idAuthor) throws SQLException;

}
