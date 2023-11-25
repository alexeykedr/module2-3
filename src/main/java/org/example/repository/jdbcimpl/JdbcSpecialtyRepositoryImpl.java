package org.example.repository.jdbcimpl;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.util.DBUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {

    private final String CREATE_SPECIALTY = "insert into specialties (name) values (?)";
    private final String GET_SPECIALTY_BY_ID = "select * from specialties where id = ?";
    private final String GET_ALL_SPECIALTIES = "select * from specialties";
    private final String UPDATE_SPECIALTY_BY_ID = "update specialties set name ? where id ?";
    private final String DELETE_SPECIALTY_BY_ID = "delete from specialties where id = ?";


    private Specialty mapResultSettoSpecialty(ResultSet resultSet) throws SQLException {
        return new Specialty(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );

    }

    @Override
    public Specialty create(Specialty specialty) {
        try (PreparedStatement preparedStatement =
                     DBUtils.getPreparedStatementWithKeys(CREATE_SPECIALTY)) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.executeQuery();

             try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.first()) {
                    specialty.setId(resultSet.getLong(1));

                }
                 return mapResultSettoSpecialty(resultSet);
             }
        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public Specialty get(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_SPECIALTY_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSettoSpecialty(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Specialty> getAll() {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_SPECIALTIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Specialty> listSpecialties = new ArrayList<>();
            while (resultSet.next()) {
                listSpecialties.add(mapResultSettoSpecialty(resultSet));
            }
            return listSpecialties;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(UPDATE_SPECIALTY_BY_ID)) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.setLong(2, specialty.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSettoSpecialty(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_SPECIALTY_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
