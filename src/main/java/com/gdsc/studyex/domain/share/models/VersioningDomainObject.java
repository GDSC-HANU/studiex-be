package com.gdsc.studyex.domain.share.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class VersioningDomainObject {
    @BsonProperty(value = "version")
    private long version;

    public VersioningDomainObject(long version) {
        this.version = version;
    }

    public long getVersion() {
        return version;
    }

    public void increaseVersion() {
        version++;
    }
}
