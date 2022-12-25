package com.gdsc.studiex.domain.share.models;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class IdentifiedVersioningAggregateRoot {
    @org.springframework.data.annotation.Id
    private Id id;
    private long version;

    protected IdentifiedVersioningAggregateRoot(Id id, long version) {
        this.id = id;
        this.version = version;
    }

    public Id getId() {
        return id;
    }


    public long getVersion() {
        return version;
    }

    public void increaseVersion() {
        version++;
    }
}
