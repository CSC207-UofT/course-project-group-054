package com.example.compound.repositories;

import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

public class BudgetRepository implements RepositoryGatewayI<BudgetTransferData> {
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryGatewayI<ItemTransferData> itemRepositoryGateway;

    private static final String SQL_FIND_BY_UID = "SELECT * FROM budgets WHERE buid = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM budgets";
    private static final String SQL_SAVE = "INSERT INTO budgets(buid, name, maxSpend, items) " +
            "VALUES(NEXTVAL('budgets_seq'), ?, ?, '{}')";
    private static final String SQL_UPDATE = "UPDATE budgets SET name = ?, maxSpend = ?, items = ? WHERE buid = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM budgets WHERE buid = ?";

    private final RowMapper<BudgetTransferData> budgetRowMapper = ((rs, rowNum) -> new BudgetTransferData(
            Integer.toString(rs.getInt("buid")), // TODO: Make UIDs integers?
            rs.getString("name"),
            rs.getDouble("maxSpend"),
            (Integer[]) rs.getArray("items").getArray()
    ));

    public BudgetRepository(RepositoryGatewayI<ItemTransferData> itemRepositoryGateway) {
        this.jdbcTemplate = new JdbcTemplate();
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    @Override
    public BudgetTransferData findByUID(String UID) {
        BudgetTransferData budgetTransferData = jdbcTemplate.queryForObject(SQL_FIND_BY_UID, budgetRowMapper, UID);

        assert budgetTransferData != null;
        for (String IUID : budgetTransferData.getBudget().keySet()) {
            budgetTransferData.addItem(IUID, itemRepositoryGateway.findByUID(IUID)); // TODO: Make all UIDs integers?
        }

        return budgetTransferData;
    }

    @Override
    public List<BudgetTransferData> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, budgetRowMapper);
    }

    @Override
    public String save(BudgetTransferData budgetTransferData) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, budgetTransferData.getName());
                ps.setDouble(2, budgetTransferData.getMaxSpend());
                return ps;
            }, keyHolder);
            return Integer.toString((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("buid"));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean update(BudgetTransferData budgetTransferData) {
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
                ps.setString(1, budgetTransferData.getName());
                ps.setDouble(2, budgetTransferData.getMaxSpend());
                ps.setArray(3, connection.createArrayOf("integer",
                        budgetTransferData.getItemIUIDs()));
                ps.setInt(4, Integer.parseInt(budgetTransferData.getBUID()));
                return ps;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String UID) {
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BY_ID);
                ps.setInt(1, Integer.parseInt(UID));
                return ps;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
