package com.gdsc.studiex.domain.studier_auth.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.models.Account;

public interface AccountRepository {
    public Account findByStudierId(Id accountId);
    public Account findByFacebookId(String facebookId);
    public void save(Account account);
}
