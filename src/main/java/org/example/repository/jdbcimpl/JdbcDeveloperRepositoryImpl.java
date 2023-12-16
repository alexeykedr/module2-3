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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private final String CREATE_DEVELOPER = "insert into developers(first_name, last_name) values (?,?)";
    private final String GET_DEVELOPER_BY_ID =
            "SELECT d.id, d.first_name, d.last_name,sp.id as spec_id, sp.name as spec_name," +
                    "       s.id as skill_id, s.name as skill_name " +
                    "FROM developers d" +
                    "         LEFT JOIN developer_skills ds ON d.id = ds.developer_id" +
                    "         LEFT JOIN skills s ON ds.skill_id = s.id" +
                    "         LEFT JOIN specialties sp ON sp.id = d.specialty_id " +
                    "where d.id = ?";
    private final String GET_ALL_DEVELOPERS =
            "SELECT d.id, d.first_name, d.last_name,sp.id as spec_id, sp.name as spec_name," +
                    "       s.id as skill_id, s.name as skill_name " +
                    "FROM developers d" +
                    "         LEFT JOIN developer_skills ds ON d.id = ds.developer_id" +
                    "         LEFT JOIN skills s ON ds.skill_id = s.id" +
                    "         LEFT JOIN specialties sp ON sp.id = d.specialty_id";
    private final String UPDATE_DEVELOPER_BY_ID = "update developers set (first_name, last_name) values (?,?) where id ?";
    private final String DELETE_DEVELOPER_BY_ID = "delete from developers where id = ?";

//натягивать сложнейший resultSet через мапу
    private Map<Long, Developer> mapResultSettoDeveloper(ResultSet resultSet) throws SQLException {
        Map<Long, Developer> mapFromRs = new HashMap<>();

            while (resultSet.next()) {
                //DEVELOPERS
                Long developerId = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                //SKILLS
                int skillId = (int)resultSet.getLong("skill_id");
                String skillName = resultSet.getString("skill_name");
                Skill skill = new Skill((long)skillId, skillName);
                List<Skill> skills = new ArrayList<>();
                skills.add((int)skillId - 1,skill); // использую - 1 чтобы избежать IOBEx
                //SPECIALTY
                Long specialtyId = resultSet.getLong("id");
                String specialtyName = resultSet.getString("specialty_name");
                Specialty specialty = new Specialty(specialtyId, specialtyName);

                Developer developer = new Developer(developerId, firstName, lastName, skills, specialty, null);
                mapFromRs.put(developerId,developer);
            }

        return mapFromRs;

    }


    @Override
    public Developer create(Developer developer) {
        try (PreparedStatement preparedStatement =
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
            ResultSet resultSet = preparedStatement.executeQuery(); //здесь не отрабатывает
            return (mapResultSettoDeveloper(resultSet)).get(id);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Developer> getAll() {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_DEVELOPERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Developer> listDevelopers = new ArrayList<>(mapResultSettoDeveloper(resultSet).values());

            return  listDevelopers;
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
            return mapResultSettoDeveloper(resultSet).get(developer.getId());
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
