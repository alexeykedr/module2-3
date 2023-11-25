package org.example.repository.jdbcimpl;

import org.example.model.Developer;
import org.example.model.Skill;
import org.example.model.Specialty;
import org.example.repository.DeveloperRepository;
import org.example.util.DBUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private final String CREATE_DEVELOPER = "insert into developers(first_name, last_name) values (?,?)";
    private final String GET_DEVELOPER_BY_ID = " SELECT d.id, d.first_name, d.last_name, d.status, " + "s.name as skill_name, sp.name as specialty_name " + "FROM developers d " + "LEFT JOIN developer_skills ds ON d.id = ds.developer_id " + "LEFT JOIN skills s ON ds.skill_id = s.id " + "LEFT JOIN specialties sp ON d.specialty_id = sp.id; ";
    private final String GET_ALL_DEVELOPERS = " SELECT d.id, d.first_name, d.last_name, d.status, " + "s.name as skill_name, sp.name as specialty_name " + "FROM developers d " + "LEFT JOIN developer_skills ds ON d.id = ds.developer_id " + "LEFT JOIN skills s ON ds.skill_id = s.id " + "LEFT JOIN specialties sp ON d.specialty_id = sp.id; ";
    private final String UPDATE_DEVELOPER_BY_ID = "update developers set (first_name, last_name) values (?,?) where id ?";
    private final String DELETE_DEVELOPER_BY_ID = "delete from developers where id = ?";


    private Developer mapResultSettoDeveloper(ResultSet resultSet) throws SQLException {
        return new Developer(resultSet.getLong("id"), resultSet.getString("first_nane"), resultSet.getString("last_nane"), null, null, null);

    }


    @Override
    public Developer create(Developer developer) {
        try (PreparedStatement preparedStatement =
//                     DBUtils.getPreparedStatement(CREATE_DEVELOPER)) {
                     DBUtils.getPreparedStatementWithKeys(CREATE_DEVELOPER)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Failed to create developer");
            }
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    developer.setId(resultSet.getLong(1));
                }
                return developer;
            } catch (SQLException ex) {
                throw new SQLException("no ID" + ex.getMessage());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Developer get(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_DEVELOPER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSettoDeveloper(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Developer> getAll() {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_DEVELOPERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Developer> listDevelopers = new ArrayList<>();
            while (resultSet.next()) {
                //DEVELOPERS
                Long developerId = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                //SKILLS
                Long skillId = resultSet.getLong("skill_id");
                String skillName = resultSet.getString("skill_name");
                Skill skill = new Skill(skillId, skillName);
                List<Skill> skills = listDevelopers.get(developerId.intValue()).getSkills();
                //SPECIALTY
                Long specialtyId = resultSet.getLong("specialty_id");
                String specialtyName = resultSet.getString("specialty_name");
                Specialty specialty = new Specialty(specialtyId, specialtyName);

                Developer developer = new Developer(developerId, firstName, lastName, skills, specialty, null);
                listDevelopers.add(developer);
            }
            return listDevelopers;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Developer update(Developer developer) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(UPDATE_DEVELOPER_BY_ID)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setLong(3, developer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSettoDeveloper(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_DEVELOPER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
