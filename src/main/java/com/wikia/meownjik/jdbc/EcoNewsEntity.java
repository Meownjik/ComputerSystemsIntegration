package com.wikia.meownjik.jdbc;

import java.util.ArrayList;
import java.util.List;

enum EcoNewsEntityFields {
    TITLE(0, "col1"),
    TEXT(1, "col2");
    
    private final int number;
    private final String name;

    EcoNewsEntityFields(int number, String name) {
        this.number = number;
        this.name = name;
    }
    
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}

public class EcoNewsEntity {
    public static final String SELECT_ALL = "SELECT * FROM tbl;";
    public static final String SELECT_BY_FIELD = "SELECT * FROM tbl WHERE %s='%s';";
    public static final String SELECT_BY_FIELD_LIKE = "SELECT * FROM tbl WHERE %s LIKE '%s';";
    public static final String DELETE_BY_TITLE = "DELETE FROM tbl WHERE "
            + EcoNewsEntityFields.TITLE.getName() + "='%s';";
    public static final String INSERT = "INSERT INTO tbl (" + EcoNewsEntityFields.TITLE.getName() + ", "
            + EcoNewsEntityFields.TEXT.getName() + ") VALUES ('%s', '%s')";

    private String text;
    private String title;

    public EcoNewsEntity() {
        text = "";
        title = "";
    }

    public EcoNewsEntity(String title, String text) {
        this.text = text;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public EcoNewsEntity setText(String text) {
        this.text = text;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EcoNewsEntity setTitle(String title) {
        this.title = title;
        return this;
   }

    @Override
    public String toString() {
        return "EcoNewsEntity ["
                + ", text=" + text
                + ", title=" + title
                + "]";
    }
    
    public static EcoNewsEntity getEcoNewsEntity(List<String> row) {
        return new EcoNewsEntity()
                .setText(row.get(EcoNewsEntityFields.TEXT.getNumber()))
                .setTitle(row.get(EcoNewsEntityFields.TITLE.getNumber()));
    }
    
    public static List<EcoNewsEntity> getListEcoNewsEntity(List<List<String>> rows) {
        List<EcoNewsEntity> result = new ArrayList<>();
        for (List<String> currentRow : rows) {
            result.add(getEcoNewsEntity(currentRow));
        }
        return result;
    }
}
