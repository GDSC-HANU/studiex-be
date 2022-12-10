package com.gdsc.studyex.domain.share.models;

public class IdentifiedVersioningDomainObject {
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
