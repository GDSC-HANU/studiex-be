package com.gdsc.studyex.domain.share.models;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class IdentifiedVersioningDomainObject {
    @org.springframework.data.annotation.Id
    private Id id;
    private long version;

    protected IdentifiedVersioningDomainObject(Id id, long version) {
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
