package com.epam.library.dataBase;

import com.epam.library.entity.Language;

import java.sql.SQLException;

public interface LanguageDAO <T extends Language> extends CommonDAO {

    int getIdLanguage(String language) throws SQLException;
}
