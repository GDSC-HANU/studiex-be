package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.time.ZonedDateTime;
import java.util.Date;

public class DateTime extends StringValueObject {
    private ZonedDateTime zonedDateTime;

    public DateTime(String value) throws InvalidInputException {
        super(value);
        try {
            this.zonedDateTime = ZonedDateTime.parse(value);
        } catch (Throwable e) {
            throw new InvalidInputException("Invalid DateTime: " + value);
        }
    }

    public static DateTime now() {
        return new DateTime(ZonedDateTime.now().toString());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isBefore(DateTime that) {
        return this.zonedDateTime.isBefore(that.zonedDateTime);
    }

    public ZonedDateTime toZonedDateTime() {
        return zonedDateTime;
    }

    public DateTime plusMinutes(int minutes) {
        return new DateTime(zonedDateTime.plusMinutes(minutes).toString());
    }
}
