package com.ksa.shiro.web.tags;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

public class HasAnyPermissions extends PermissionTag {

    private static final long serialVersionUID = -8914021782643585116L;
    
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
