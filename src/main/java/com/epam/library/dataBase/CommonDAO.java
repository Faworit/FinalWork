package com.epam.library.dataBase;

import com.epam.library.entity.Entity;

import java.sql.SQLException;
import java.util.List;

public interface CommonDAO <T extends Entity> {
    List<T> getAll(int id) throws SQLException;
    void create(Object object) throws SQLException;
    void delete(int id) throws SQLException;


}
