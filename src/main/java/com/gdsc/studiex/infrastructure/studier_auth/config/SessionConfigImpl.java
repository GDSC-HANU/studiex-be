package com.gdsc.studiex.infrastructure.studier_auth.config;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.config.SessionConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SessionConfigImpl implements SessionConfig {
    public static String tokenSecret = "fake-secret-key";

//    public SessionConfigImpl(Environment environment) {
//        if (environment.getProperty("studierauth.session.tokensecret") != null) {
//            tokenSecret = environment.getProperty("studierauth.session.tokensecret");
//        }
//    }
    @Override
    public String getTokenSecret() {
        return tokenSecret;
    }
}
