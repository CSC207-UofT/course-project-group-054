package com.example.compound.repositories;

import com.example.compound.entities.Group;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.GroupTransferData;

import java.util.List;

public class GroupRepository implements RepositoryGatewayI<GroupTransferData> {
    @Override
    public GroupTransferData findByUID(String UID) {
        return null;
    }

    @Override
    public List<GroupTransferData> findAll() {
        return null;
    }

    @Override
    public String save(GroupTransferData group) {
        return null;
    }

    @Override
    public void deleteById(String UID) {

    }
}
