package com.homework;

import com.homework.exceptions.IllegalClientDataException;
import com.homework.exceptions.NoElementFoundByIdException;

import java.sql.Connection;
import java.util.List;

public interface CrudServiceInterface <T> {
    long create(T object, Connection connection) throws IllegalClientDataException;
    void updateById(T object, Connection connection) throws IllegalClientDataException;
    ClientRecord getById(Long id, Connection connection) throws NoElementFoundByIdException, IllegalClientDataException;
    void deleteById(Long id, Connection connection) throws IllegalClientDataException;
    List<ClientRecord> listAll(Connection connection);
}
