package org.bitstrings.maven.plugins.filetouri;

public class FilePathToUri
{
    protected String path;
    protected String propertyName;
    protected Boolean uriPathOnly;

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName( String propertyName )
    {
        this.propertyName = propertyName;
    }

    public Boolean getUriPathOnly()
    {
        return uriPathOnly;
    }

    public void setUriPathOnly( Boolean uriPathOnly )
    {
        this.uriPathOnly = uriPathOnly;
    }
}
