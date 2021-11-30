package com.example.compound.repositories;

import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class BudgetRepository implements RepositoryGatewayI<BudgetTransferData> {
    private final JdbcTemplate jdbcTemplate;
    private final RepositoryGatewayI<ItemTransferData> itemRepositoryGateway;

    private static final String SQL_FIND_BY_UID = "SELECT * FROM budgets WHERE buid = ?";

    public BudgetRepository(RepositoryGatewayI<ItemTransferData> itemRepositoryGateway) {
        this.jdbcTemplate = new JdbcTemplate();
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    @Override
    public BudgetTransferData findByUID(String UID) {
        BudgetTransferData budgetTransferData = jdbcTemplate.queryForObject(SQL_FIND_BY_UID, budgetRowMapper, UID);

        assert budgetTransferData != null; // TODO: Any alternatives?
        for (String IUID : budgetTransferData.getBudget().keySet()) {
            budgetTransferData.getBudget().put(IUID, itemRepositoryGateway.findByUID(IUID)); // TODO: Make all UIDs integers?
        }

        return budgetTransferData;
    }

    private final RowMapper<BudgetTransferData> budgetRowMapper = ((rs, rowNum) -> new BudgetTransferData(
            Integer.toString(rs.getInt("buid")), // TODO: Make UIDs integers?
            rs.getString("name"),
            rs.getDouble("maxSpend"),
            (Integer[]) rs.getArray("items").getArray()
    ));

    @Override
    public List<BudgetTransferData> findAll() {
        return null;
    }

    @Override
    public String save(BudgetTransferData budgetTransferData) {
        return null;
    }

    public void update(BudgetTransferData budgetTransferData) { // TODO: remove if save does not need to be split
    }

    @Override
    public void deleteById(String UID) {

    }
}
