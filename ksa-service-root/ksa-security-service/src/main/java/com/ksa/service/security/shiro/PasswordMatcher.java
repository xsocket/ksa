package com.ksa.service.security.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.ksa.service.security.util.SecurityUtils;

public class PasswordMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch( AuthenticationToken token, AuthenticationInfo info ) {
        Object credentials = token.getCredentials();
        String inputPassword = null;
        if( credentials.getClass().isArray() ) {
            inputPassword = new String( (char[]) credentials );
        } else {
            inputPassword = credentials.toString();
        }
        String digestPassword = SecurityUtils.digestPassword( inputPassword );
        return info.getCredentials().equals( digestPassword );
    }

}
