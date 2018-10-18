package com.scanapp.services;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.logging.Logger;

public class ActiveDirectoryLdapService {
    private static Logger logger = (Logger) Logger.getLogger(ActiveDirectoryLdapService.AD_ATTR_NAME_USER_EMAIL);
    //Attribute names
    private static final String AD_ATTR_NAME_TOKEN_GROUPS = "tokenGroups";
    private static final String AD_ATTR_NAME_OBJECT_CLASS = "objectClass";
    private static final String AD_ATTR_NAME_OBJECT_CATEGORY = "objectCategory";
    private static final String AD_ATTR_NAME_MEMBER = "member";
    private static final String AD_ATTR_NAME_MEMBER_OF = "memberOf";
    private static final String AD_ATTR_NAME_DESCRIPTION = "description";
    private static final String AD_ATTR_NAME_OBJECT_GUID = "objectGUID";
    private static final String AD_ATTR_NAME_OBJECT_SID = "objectSid";
    private static final String AD_ATTR_NAME_DISTINGUISHED_NAME = "distinguishedName";
    private static final String AD_ATTR_NAME_CN = "cn";
    private static final String AD_ATTR_NAME_USER_PRINCIPAL_NAME = "userPrincipalName";
    private static final String AD_ATTR_NAME_USER_EMAIL = "mail";
    private static final String AD_ATTR_NAME_GROUP_TYPE = "groupType";
    private static final String AD_ATTR_NAME_SAM_ACCOUNT_TYPE = "sAMAccountType";
    private static final String AD_ATTR_NAME_USER_ACCOUNT_CONTROL = "userAccountControl";

    /**
     *
     * @param ctx
     * @param searchBase
     * @param domainWithUser: suck as "MYDOMAIN\myUser"
     * @return
     */
    public String getUserMailByDomainWithUser(LdapContext ctx, String searchBase, String domainWithUser)
    {
        System.out.println("trying to get email of domainWithUser " +
                domainWithUser + " using baseDN " + searchBase);
        String userName = domainWithUser.substring(domainWithUser.indexOf('\\') +1 );
        try
        {
            NamingEnumeration<SearchResult>
                    userDataBysAMAccountName = getUserDataBysAMAccountName(ctx, searchBase, userName);
            return getUserMailFromSearchResults( userDataBysAMAccountName );
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    private NamingEnumeration<SearchResult>
    getUserDataBysAMAccountName(LdapContext ctx, String searchBase, String username)
            throws Exception
    {
        String filter = "";
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<SearchResult> answer = null;
        try
        {
            answer = ctx.search(searchBase, filter, searchCtls);
        }
        catch (Exception e)
        {
           System.out.println("Error searching Active directory for " + filter);
            throw e;
        }

        return answer;
    }

    private String getUserMailFromSearchResults( NamingEnumeration<SearchResult> userData )
            throws Exception
    {
        try
        {
            String mail = null;
            String cn = null;
            // getting only the first result if we have more than one
            if (userData.hasMoreElements())
            {
                SearchResult sr = userData.nextElement();
                Attributes attributes = sr.getAttributes();
                mail = attributes.get(AD_ATTR_NAME_USER_EMAIL).get().toString();
                cn = attributes.get(AD_ATTR_NAME_CN).get().toString();
               System.out.println("found email " + mail);
               System.out.println("found cn " + cn);
            }

            return mail;
        }
        catch (Exception e)
        {
           System.out.println("Error fetching attribute from object");
            throw e;
        }
    }
}
