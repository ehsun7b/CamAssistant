package com.ehsunbehravesh.camassistant.version;

/**
 *
 * @author Ehsun Behravesh
 */
public class Version {

    private int version;
    private String name;
    private VersionType type;
    private String releaseDate;

    public Version(int version, String name, VersionType type, String releaseDate) {
        this.version = version;
        this.name = name;
        this.type = type;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return version + " - " + name + " - " + type + " - " + releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VersionType getType() {
        return type;
    }

    public void setType(VersionType type) {
        this.type = type;
    }

}
