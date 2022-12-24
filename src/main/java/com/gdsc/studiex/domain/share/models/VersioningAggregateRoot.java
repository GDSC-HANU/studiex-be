package com.gdsc.studiex.domain.share.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class VersioningAggregateRoot {
    @BsonProperty(value = "version")
    private long version;

    protected VersioningAggregateRoot(long version) {
        this.version = version;
    }

    public long getVersion() {
        return version;
    }

    public void increaseVersion() {
        version++;
    }
}
