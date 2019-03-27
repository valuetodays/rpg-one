package billy.rpg.resource.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

public abstract class ResourceTestBase {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param relativePath relative path according to src/test/resources, can start with '/' or not
     */
    protected String getTestResource(String relativePath) throws URISyntaxException {
        String path = MapMetaData.class.getResource("/").toURI().getPath();

        relativePath = relativePath.replace(File.separator, "/");
        if (relativePath.startsWith("/")) {
            return path + relativePath.substring(1);
        }
        return path + relativePath;
    }
}
