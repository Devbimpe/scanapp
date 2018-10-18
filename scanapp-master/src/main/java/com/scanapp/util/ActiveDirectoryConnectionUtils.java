package com.scanapp.util;

import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

@Component
public class ActiveDirectoryConnectionUtils {
    public LdapContext createContext(String url, String user, String pass)
    {    	        Hashtable<String,String> env = getProperties(url, user, pass);
        LdapContext ctx;
        try
        {
            ctx = new InitialLdapContext(env, null);
        }
        catch (NamingException e)
        {
            throw new RuntimeException(e);
        }
        return ctx;
    }
    private Hashtable<String,String> getProperties(String serverUrl, String user, String password)
    {
        //create an initial directory context
        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.REFERRAL, "ignore");
        env.put("com.sun.jndi.ldap.connect.pool", "false");
        //environment property to specify how long to wait for a pooled connection.
        // If you omit this property, the application will wait indefinitely.
        env.put("com.sun.jndi.ldap.connect.timeout", "300000");
        env.put(Context.PROVIDER_URL, serverUrl);
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put("java.naming.ldap.attributes.binary", "tokenGroups objectSid objectGUID");
        return env;
    }
}
