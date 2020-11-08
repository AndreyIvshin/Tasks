package com.epam.newsportal.mapper;

import com.epam.newsportal.model.entity.AbstractEntity;
import com.epam.newsportal.model.transfer.AbstractTransfer;

public abstract class AbstractMapper<Entity extends AbstractEntity, Transfer extends AbstractTransfer> {

    public abstract Entity map(Transfer transfer);

    public abstract Transfer map(Entity entity);

}
