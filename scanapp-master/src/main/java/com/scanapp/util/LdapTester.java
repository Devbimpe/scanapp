package com.scanapp.util;

import com.scanapp.services.ActiveDirectoryLdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.ldap.LdapContext;

public class LdapTester {


    @Value("${esentry.xyz}")
    private String url;
    @Value("${matthew.esentry.xyz}")
    private String username;
    @Value("${infoprive1$}")
    private String password;
    @Value("${dn=esentry, dc=xyz}")
    private String baseDN;
    @Autowired
    private ActiveDirectoryConnectionUtils adConnectionUtils;
    @Autowired
    private ActiveDirectoryLdapService adLdapService;
    public void testGetUserMailByDomainWithUser(String fqn)
    {
        LdapContext ctx = adConnectionUtils.createContext(url, username, password);
        String email = adLdapService.getUserMailByDomainWithUser(ctx, baseDN, fqn);

    }
}



