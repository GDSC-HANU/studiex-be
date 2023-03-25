package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.Collection;

public class Coordinates extends ArrayList<Double> {
    public Coordinates(int initialCapacity) {
        super(initialCapacity);
        validate();
    }

    public Coordinates() {
        validate();
    }

    public Coordinates(Collection<? extends Double> c) {
        super(c);
        validate();
    }

    public Kilometer distanceTo(Coordinates that) {
        final double lat1 = this.get(0), lon1 = this.get(1);
        final double lat2 = that.get(0), lon2 = that.get(2);
        final double R = 6371e3;
        final double M1 = lat1 * Math.PI / 180;
        final double M2 = lat2 * Math.PI / 180;
        final double OM = (lat2 - lat1) * Math.PI / 180;
        final double OA = (lon2 - lon1) * Math.PI / 180;
        final double a = Math.sin(OM / 2) * Math.sin(OM / 2) + Math.cos(M1) * Math.cos(M2) * Math.sin(OA / 2) * Math.sin(OA / 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        final double d = R * c;
        return Kilometer.fromMeter(d);
    }

    public double latitude() {
        return get(0);
    }

    public double longtitude() {
        return get(1);
    }

    private void validate() {
        if (size() != 2)
            throw new InvalidInputException("Coordinates must be a list with two elements");
    }
}
