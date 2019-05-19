package com.epam.library.dataBase;

import com.epam.library.entity.Language;

import java.sql.SQLException;

public interface LanguageDAO extends CommonDAO<Language> {

    int getIdLanguage(String language) throws SQLException;
}
