package com.epam.library.entity;

public class Genre {

    private int IDGenre;
    private int IDLanguage;
    private String genreName;

    public Genre(int IDGenre, int IDLanguage, String genreName) {
        this.IDGenre = IDGenre;
        this.IDLanguage = IDLanguage;
        this.genreName = genreName;
    }

    public int getIDGenre() {
        return IDGenre;
    }

    public void setIDGenre(int IDGenre) {
        this.IDGenre = IDGenre;
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
                "IDGenre=" + IDGenre +
                ", IDLanguage=" + IDLanguage +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
