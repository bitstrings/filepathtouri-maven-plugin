package org.bitstrings.maven.plugins.filetouri;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_RESOURCES;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo( name = "convert", defaultPhase = GENERATE_RESOURCES, threadSafe = true )
public class FilePathToUriMojo
    extends AbstractMojo
{
    protected static final String URI_SUFFIX_DEFAULT = ".uri";

    @Parameter( defaultValue = "${project}", readonly = true )
    protected MavenProject mavenProject;

    @Parameter( defaultValue = "${session}", readonly = true )
    protected MavenSession mavenSession;

    @Parameter( required = false )
    protected List<FilePathToUri> filePathToUris;

    @Parameter( defaultValue = "true", required = false )
    protected boolean failOnError;

    @Override
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        if ( filePathToUris == null )
        {
            return;
        }

        Properties mavenProperties = mavenProject.getProperties();

        for ( FilePathToUri filePathToUri : filePathToUris )
        {
            filePathToUriValidateAndDefaults( filePathToUri );

            try
            {
                URI uri = new File( filePathToUri.getPath()).toURI();

                String uriString =
                    filePathToUri.getUriPathOnly()
                        ? uri.getPath()
                        : uri.toString();

                mavenProperties.setProperty( filePathToUri.getPropertyName(), uriString );

                System.out.println( "  uriName = " + mavenProperties.getProperty( filePathToUri.getPropertyName() ) );
            }
            catch ( Exception e )
            {
                if ( failOnError )
                {
                    throw new MojoExecutionException( e.getLocalizedMessage(), e );
                }
            }
        }
    }

    protected void filePathToUriValidateAndDefaults( FilePathToUri filePathToUri )
        throws MojoExecutionException
    {
        if ( filePathToUri.getPath() == null )
        {
            throw new MojoExecutionException( "File path must be set." );
        }

        if ( filePathToUri.getPropertyName() == null )
        {
            throw new MojoExecutionException( "Property name must be set." );
        }

        if ( filePathToUri.getUriPathOnly() == null )
        {
            filePathToUri.setUriPathOnly( false );
        }
    }
}
