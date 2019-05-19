package com.epam.library.entity;

import java.util.Objects;

public class Genre extends Entity{

    private int IDLanguage;
    private String genreName;

    public Genre(int IDLanguage, String genreName) {
        this.IDLanguage = IDLanguage;
        this.genreName = genreName;
    }

    public int getIDLanguage() {
        return IDLanguage;
    }

    public void setIDLanguage(int IDLanguage) {
        this.IDLanguage = IDLanguage;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "IDLanguage=" + IDLanguage +
                ", genreName='" + genreName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return IDLanguage == genre.IDLanguage &&
                Objects.equals(genreName, genre.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDLanguage, genreName);
    }
}
