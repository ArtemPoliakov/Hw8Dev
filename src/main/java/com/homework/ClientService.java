package com.homework;

import com.homework.exceptions.IllegalClientDataException;
import com.homework.exceptions.NoElementFoundByIdException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ClientService implements CrudServiceInterface<com.homework.ClientRecord> {
    private static final String CREATE_CLIENT_TEMPLATE = "INSERT INTO client(name) VALUES (?);";
    private static final String GET_BY_ID_TEMPLATE = "SELECT * FROM client WHERE id=?;";
    private static final String DELETE_CLIENT_TEMPLATE = "DELETE FROM client WHERE id=?;";
    private static final String GET_ALL_TEMPLATE = "SELECT id, name FROM client;";
    private static final String SET_NAME_BY_ID_TEMPLATE = "UPDATE client SET name=? WHERE id=?;";

    @Override
    public long create(ClientRecord client, Connection connection) throws IllegalClientDataException{
        try (PreparedStatement preparedStatement = connection.prepareStatement
                (CREATE_CLIENT_TEMPLATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            validateClientName(client.name());
            preparedStatement.setString(1, client.name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public ClientRecord getById(Long id, Connection connection) throws NoElementFoundByIdException, IllegalClientDataException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_TEMPLATE)) {
            validateClientId(id);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new ClientRecord(
                        resultSet.getLong("id"),
                        resultSet.getString("name"));
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        throw new NoElementFoundByIdException();
    }

    @Override
    public void deleteById(Long id, Connection connection) throws IllegalClientDataException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_TEMPLATE)) {
            validateClientId(id);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ClientRecord> listAll(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<ClientRecord> list = new LinkedList<>();
            ResultSet resultSet = statement.executeQuery(GET_ALL_TEMPLATE);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                list.add(new ClientRecord(id, name));
            }
            return list;
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return List.of();
    }

    @Override
    public void updateById(ClientRecord object, Connection connection) {
        throw new UnsupportedOperationException();
    }

    public void setName(long id, String name, Connection connection) throws IllegalClientDataException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(SET_NAME_BY_ID_TEMPLATE)) {
            validateClientId(id);
            validateClientName(name);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void validateClientName(String name) throws IllegalClientDataException {
        int nameLength = name.length();
        if (name == null || nameLength < 2 || nameLength > 1000) {
            throw new IllegalClientDataException();
        }
    }

    private void validateClientId(Long id) throws IllegalClientDataException {
        if (id == null || id < 0) {
            throw new IllegalClientDataException();
        }
    }
}
