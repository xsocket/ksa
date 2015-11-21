package com.ksa.shiro.freemarker;

import org.apache.shiro.subject.Subject;

public class HasAnyPermissionsTag extends PermissionTag {
    
    // Delimeter that separates role names in tag attribute
    private static final String PERMISSION_NAMES_DELIMETER = ",";

    protected boolean showTagBody( String permissions ) {
        boolean hasAnyPermission = false;

        Subject subject = getSubject();

        if( subject != null ) {
            // Iterate through roles and check to see if the user has one of the roles
            for( String p : permissions.split( PERMISSION_NAMES_DELIMETER ) ) {
                if( subject.isPermitted( p.trim() ) ) {
                    hasAnyPermission = true;
                    break;
                }
            }
        }

        return hasAnyPermission;
    }

}
