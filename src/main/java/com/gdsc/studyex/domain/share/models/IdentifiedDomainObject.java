package com.gdsc.studyex.domain.share.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class IdentifiedDomainObject {
    @BsonProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Id id;


    public IdentifiedDomainObject(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
