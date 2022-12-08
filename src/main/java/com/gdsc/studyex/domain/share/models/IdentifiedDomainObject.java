package com.gdsc.studyex.domain.share.models;
public class IdentifiedDomainObject {
    private Id id;

    public IdentifiedDomainObject() {}

    public IdentifiedDomainObject(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
