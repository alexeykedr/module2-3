package org.example.repository.jdbcimpl;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.util.DBUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    private final String CREATE_SKILL = "insert into skills (name) values (?)";
    private final String GET_SKILL_BY_ID = "select * from skills where id = ?";
    private final String GET_ALL_SKILLS = "select * from skills";
    private final String UPDATE_SKILL_BY_ID = "update skills set name ? where id ?";
    private final String DELETE_SKILL_BY_ID = "delete from skills where id = ?";


    private Skill mapResultSettoSkill(ResultSet resultSet) throws SQLException {
        return new Skill(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );

    }
    @Override
    public Skill create(Skill skill) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatementWithKeys(CREATE_SKILL)) {
            preparedStatement.setString(1, skill.getName());
//            preparedStatement.executeQuery();
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.first()) {
//                    resultSet.getLong(1);
                    skill.setId(resultSet.getLong(1));
                }
                return mapResultSettoSkill(resultSet);
            }
        } catch (SQLException e) {
            return null;
        }

    }

        @Override
    public Skill get(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_SKILL_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSettoSkill(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Skill> getAll() {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(GET_ALL_SKILLS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Skill> listSkills = new ArrayList<>();
            while (resultSet.next()) {
                listSkills.add(mapResultSettoSkill(resultSet));
            }
            return listSkills;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Skill update(Skill skill) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(UPDATE_SKILL_BY_ID)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setLong(2, skill.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSettoSkill(resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = DBUtils.getPreparedStatement(DELETE_SKILL_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
