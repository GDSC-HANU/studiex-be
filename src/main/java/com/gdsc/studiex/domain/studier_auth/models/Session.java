package com.gdsc.studiex.domain.studier_auth.models;

import com.gdsc.studiex.domain.share.exceptions.UnauthorizedException;
import com.gdsc.studiex.domain.share.models.DateTime;
import com.gdsc.studiex.domain.share.models.Id;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.util.Date;
@Getter
public class Session {
    private Id sessionId;
    private Id studierId;
    private DateTime expireAt;

    private Session(Id studierId, DateTime expireAt) {
        this.sessionId = Id.generateRandom();
        this.studierId = studierId;
        this.expireAt = expireAt;
    }
    public static Session createSession(Id studierId) {
        return new Session(
                studierId,
                DateTime.now().plusMinutes(10080));
    }

    public String genToken(String secret) {
        return Jwts.builder()
                .setId(getSessionId().toString())
                .setExpiration(Date.from(getExpireAt().toZonedDateTime().toInstant()))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public static Id verifyTokenAndGetSessionId(String token, String secret) throws UnauthorizedException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return new Id(claims.getId());
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }
    }


    public boolean invalidated() {
        if(expireAt.isBefore(DateTime.now())) {
            return true;
        }
        return false;
    }

}
