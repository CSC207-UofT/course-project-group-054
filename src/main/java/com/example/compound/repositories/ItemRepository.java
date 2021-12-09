package com.example.compound.repositories;

import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.ItemTransferData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * A repository for storing Group data transfer objects.
 */
@Repository
public class ItemRepository implements RepositoryGatewayI<ItemTransferData> {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_BY_UID = "SELECT * FROM items WHERE iuid = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM items";
    private static final String SQL_SAVE = "INSERT INTO items(iuid, name, cost, quantity) " +
            "VALUES(NEXTVAL('items_seq'), ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE items SET name = ?, cost = ?, quantity = ? WHERE iuid = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM items WHERE iuid = ?";

    private final RowMapper<ItemTransferData> itemRowMapper = ((rs, rowNum) -> new ItemTransferData(
            Integer.toString(rs.getInt("iuid")),
            rs.getString("name"),
            rs.getDouble("cost"),
            rs.getInt("quantity")
    ));

    /**
     * Construct a new repository for Item data transfer objects.
     */
    public ItemRepository() {
        this.jdbcTemplate = new JdbcTemplate();
    }

    /**
     * Return the Item data transfer object with the given UID from the repository.
     * @param UID the UID of the Item data transfer object to be retrieved
     * @return the Item data transfer object retrieved from the repository
     */
    @Override
    public ItemTransferData findByUID(String UID) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_UID, itemRowMapper, UID);
    }

    /**
     * Return all the Item data transfer objects in the repository.
     * @return all the Item data transfer objects in the repository
     */
    @Override
    public List<ItemTransferData> findAll() {
        return null;
    }

    /**
     * Save a new Item data transfer object in the repository.
     * @param itemTransferData the Item data transfer object to save
     * @return the UID of the new Item data transfer object
     */
    @Override
    public String save(ItemTransferData itemTransferData) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, itemTransferData.getName());
                ps.setDouble(2, itemTransferData.getCost());
                ps.setInt(3, itemTransferData.getQuantity());
                return ps;
            }, keyHolder);
            return Integer.toString((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("iuid"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Update an existing Item data transfer object in the repository to match the given object.
     * @param itemTransferData the Item data transfer object with which to update the repository
     * @return whether the Item data transfer object was updated in the repository
     */
    @Override
    public boolean update(ItemTransferData itemTransferData) {
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
                ps.setString(1, itemTransferData.getName());
                ps.setDouble(2, itemTransferData.getCost());
                ps.setInt(3, itemTransferData.getQuantity());
                ps.setInt(4, Integer.parseInt(itemTransferData.getIUID()));
                return ps;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Delete the Item data transfer object with the given UID.
     * @param UID the UID of the Item data transfer object to be deleted
     * @return whether the Item data transfer object was deleted
     */
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
