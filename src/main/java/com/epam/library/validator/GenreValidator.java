package com.epam.library.validator;

import com.epam.library.dataBase.GenreDAO;
import com.epam.library.entity.Genre;

import java.sql.SQLException;
import java.util.List;

public class GenreValidator {

    public static boolean checkGenre(List<Genre> genres) throws SQLException {
        boolean isExists = false;
        GenreDAO genreDAO = new GenreDAO();
        for(int n=0; n<genres.size(); n++){
            List<Genre> dbGenres = genreDAO.getAll(genres.get(n).getIDLanguage());
            for (int i = 0; i < dbGenres.size(); i++) {
               String genreName = genres.get(n).getGenreName();
               String dbGenreName = dbGenres.get(i).getGenreName();
               if(genreName.equals(dbGenreName)){
                   isExists = true;
               }
            }
        }
        return isExists;
    } 
}
