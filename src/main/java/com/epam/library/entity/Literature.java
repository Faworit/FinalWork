package com.epam.library.entity;

public abstract class Literature {

    private String title;
    private int languageID;
    private int ID;

    public Literature(String title, int languageID, int ID) {
        this.title = title;
        this.languageID = languageID;
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Literature{" +
                "title='" + title + '\'' +
                ", languageID=" + languageID +
                ", ID=" + ID +
                '}';
    }
}
