package com.gdsc.studiex.domain.share.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class IdentifiedAggregateRoot {
    @org.springframework.data.annotation.Id
    private Id id;


    protected IdentifiedAggregateRoot(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
