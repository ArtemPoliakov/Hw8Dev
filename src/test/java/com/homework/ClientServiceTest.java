package com.homework;

import com.homework.exceptions.IllegalClientDataException;
import com.homework.exceptions.NoElementFoundByIdException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

class ClientServiceTest {
    @BeforeAll
    static void setUp() {
        Flyway flyway = Flyway.configure()
                .dataSource(DbConfig.getDbUrl(), DbConfig.getDbUser(), DbConfig.getDbPassword())
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }

    @AfterAll
    static void CleanUp(){
        try(Connection connection = ConfiguredDataSource.getPool().getConnection()){
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @MethodSource(value = "getClients")
    void testIntegrationCreateAndGetOperateSameDataBySameId(ClientRecord client) {
        try {
            Connection connectionCreate = ConfiguredDataSource.getPool().getConnection();
            Connection connectionGet = ConfiguredDataSource.getPool().getConnection();
            ClientService clientService = new ClientService();
            Long id = clientService.create(client, connectionCreate);
            ClientRecord clientRecord = clientService.getById(id, connectionGet);
            Assertions.assertEquals(client.name(), clientRecord.name());
        } catch (NoElementFoundByIdException e) {
            Assertions.fail("No match by the id");
        } catch (SQLException | IllegalClientDataException e) {
            Assertions.fail("Unexpected exception thrown");
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "32343454897"})
    void testGetByIdReturnsDataOrThrowsNoElementFoundNyIdExceptionAsNormalReaction(long id) {
        try {
            Connection connection = ConfiguredDataSource.getPool().getConnection();
            ClientService clientService = new ClientService();
            ClientRecord clientRecord = clientService.getById(id, connection);
            Assertions.assertNotNull(clientRecord);
        } catch (SQLException | IllegalClientDataException e) {
            Assertions.fail("Unexpected exception thrown");
        } catch (NoElementFoundByIdException e) {
        }
    }

    @ParameterizedTest
    @MethodSource(value = "LongWrapperSupplyForInvalidId")
    @NullSource
    void testGetByIdThrowsIllegalClientDataExceptionWhenInvalidId(Long id) {
        try {
            Connection connection = ConfiguredDataSource.getPool().getConnection();
            ClientService clientService = new ClientService();
            Assertions.assertThrows(IllegalClientDataException.class, () -> clientService.getById(id, connection));
        } catch (SQLException e) {
            Assertions.fail("Unexpected exception thrown");
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2", "3"})
    void testDeleteByIdDeletesClientById(long id) {
        try {
            Connection connectionDelete = ConfiguredDataSource.getPool().getConnection();
            Connection connectionGet = ConfiguredDataSource.getPool().getConnection();
            ClientService clientService = new ClientService();
            clientService.deleteById(id, connectionDelete);
            Assertions.assertThrows(NoElementFoundByIdException.class,
                    () -> clientService.getById(id, connectionGet));
        } catch (SQLException e) {
            Assertions.fail("Unexpected exception thrown");
        } catch (IllegalClientDataException e) {
            Assertions.fail("Unexpected IllegalClientDataException");
        }
    }

    @Test
    void testListAllReturnsNotNull() {
        try {
            Connection connection = ConfiguredDataSource.getPool().getConnection();
            ClientService clientService = new ClientService();
            Assertions.assertNotNull(clientService.listAll(connection));
        } catch (SQLException e) {
            Assertions.fail("Unexpected exception thrown");
        }
    }

    @ParameterizedTest
    @MethodSource(value = "getClients")
    void updateById(ClientRecord clientRecord) {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> new ClientService().updateById(clientRecord, ConfiguredDataSource.getPool().getConnection()));
    }

    @ParameterizedTest
    @CsvSource(value = {"Stepan Bandera TEST_TEST", "Bohdan Khmelnitskiy TEST_TEST"})
    void testIntegrationSetNameSetsNameToTheExpectedIdSuccessfully(String name) {
        String defaultName = "random";
        try {
            Connection connectionCreate = ConfiguredDataSource.getPool().getConnection();
            Connection connectionSet = ConfiguredDataSource.getPool().getConnection();
            Connection connectionGet = ConfiguredDataSource.getPool().getConnection();
            ClientService clientService = new ClientService();
            long id = clientService.create(new ClientRecord(null, defaultName), connectionCreate);
            clientService.setName(id, name, connectionSet);
            String nameFromDb = clientService.getById(id, connectionGet).name();
            Assertions.assertEquals(name, nameFromDb);
        } catch (SQLException | NoElementFoundByIdException e) {
            Assertions.fail("Unexpected exception thrown");
        } catch (IllegalClientDataException e) {
            Assertions.fail("Unexpected IllegalClientDataException");
        }
    }

    static Stream<Arguments> getClients() {
        return Stream.of(
                Arguments.of(new ClientRecord(null, "Artem")),
                Arguments.of(new ClientRecord(null, "Danulo"))
        );
    }

    static Stream<Arguments> LongWrapperSupplyForInvalidId() {
        return Stream.of(
                Arguments.of(-1L),
                Arguments.of(-356L)

        );
    }
}