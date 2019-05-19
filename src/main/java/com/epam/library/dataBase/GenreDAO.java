package com.epam.library.dataBase;

import com.epam.library.entity.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreDAO <T extends Genre> extends CommonDAO {

    List<T> getGenre(int IDBook, int language) throws SQLException;
    void setEditGenre(int idGenre, int idLanguage, String genreName) throws SQLException;
    int getLastId() throws SQLException;

}
