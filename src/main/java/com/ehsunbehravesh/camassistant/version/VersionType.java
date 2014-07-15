package com.ehsunbehravesh.camassistant.version;

/**
 *
 * @author Ehsun Behravesh
 */
public enum VersionType {
    STABLE("stable"), BETA("beta"), ALPHA("alpha");
    
    private final String description;

    private VersionType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
    
    
}
