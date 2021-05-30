package com.wikia.meownjik.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EcoNewsDao {

    public void insert(EcoNewsEntity ecoNewsEntity) {
        Statement statement = ManagerDao.get().getStatement();
        try {
            statement.execute(String.format(EcoNewsEntity.INSERT,
                    ecoNewsEntity.getTitle(), ecoNewsEntity.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        ManagerDao.closeStatement(statement);
    }

    public List<EcoNewsEntity> selectByField(String field, String value, boolean exactMatch) {
        Statement statement = ManagerDao.get().getStatement();
        List<List<String>> rows = null;
        try {
            if (exactMatch) {
                ResultSet resultSet = statement.executeQuery(String.format(EcoNewsEntity.SELECT_BY_FIELD, field, value));
                rows = ManagerDao.get().parseResultSet(resultSet);
            }
            else {
                ResultSet resultSet = statement.executeQuery(String.format(EcoNewsEntity.SELECT_BY_FIELD_LIKE, field,
                        "%" + value + "%"));
                rows = ManagerDao.get().parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        //
        ManagerDao.closeStatement(statement);
        return EcoNewsEntity.getListEcoNewsEntity(rows);
    }

    public List<EcoNewsEntity> selectByTitle(String title) {
        return selectByField(EcoNewsEntityFields.TITLE.getName(), title, true);
    }

    public List<EcoNewsEntity> selectAll() {
        Statement statement = ManagerDao.get().getStatement();
        List<List<String>> rows = null;
        try {
            ResultSet resultSet = statement.executeQuery(EcoNewsEntity.SELECT_ALL);
            rows = ManagerDao.get().parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        ManagerDao.closeStatement(statement);
        return EcoNewsEntity.getListEcoNewsEntity(rows);
    }

    public void delete(EcoNewsEntity ecoNewsEntity) {
        deleteByTitle(ecoNewsEntity.getTitle());
    }

    public void deleteByTitle(String title) {
        Statement statement = ManagerDao.get().getStatement();
        try {
            statement.execute(String.format(EcoNewsEntity.DELETE_BY_TITLE, title));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        ManagerDao.closeStatement(statement);
    }
}