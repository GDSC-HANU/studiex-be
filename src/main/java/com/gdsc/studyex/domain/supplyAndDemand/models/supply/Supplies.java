package com.gdsc.studyex.domain.supplyAndDemand.models.supply;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gdsc.studyex.domain.share.models.Id;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "supplies")
public class Supplies {
    @BsonProperty(value = "studierId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Id studierId;
    @BsonProperty(value = "supplies")
    private List<Supply> supplies;
}
