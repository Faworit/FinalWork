package com.epam.library.dataBase;

import com.epam.library.entity.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreDAO extends CommonDAO<Genre> {

    List<Genre> getGenre(int IDBook, int language) throws SQLException;
    void setEditGenre(int idGenre, int idLanguage, String genreName) throws SQLException;
    int getLastId() throws SQLException;
}
