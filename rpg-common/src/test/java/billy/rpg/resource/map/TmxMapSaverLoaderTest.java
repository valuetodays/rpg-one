package billy.rpg.resource.map;

import billy.rpg.common.exception.UnFinishException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;


public class TmxMapSaverLoaderTest extends ResourceTestBase {
    private MapSaverLoader mapSaverLoader;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        mapSaverLoader = new TmxMapSaverLoader();
    }

    @Test
    public void testSave() throws IOException {
        expectedException.expect(UnFinishException.class);
        expectedException.expectMessage("create map by Tiled please");
        mapSaverLoader.save("", null);
    }

    @Test
    public void testLoad_shouldSuccessWhenFileExists() throws Exception {
        String testResource = getTestResource("/assets/map/tmx/test-1-1.tmx");
        logger.debug(testResource);
        MapMetaData loadedMapMetaData = mapSaverLoader.load(testResource);
        logger.debug(loadedMapMetaData.toString());
        Assert.assertEquals("百草地", loadedMapMetaData.getName());
        Assert.assertEquals("test-1-1", loadedMapMetaData.getMapId());
        Assert.assertEquals("tile2.png", loadedMapMetaData.getTileId());
        Assert.assertEquals(2, loadedMapMetaData.getLayers().size());
        Assert.assertEquals(15, loadedMapMetaData.getHeight());
        Assert.assertEquals(24, loadedMapMetaData.getWidth());
    }

    @Test
    public void testLoad_shouldFailWhenFileNotExists() throws Exception {
        String testResource = getTestResource("/file-not-exist.tmx");
        logger.debug(testResource);
        expectedException.expect(IOException.class);
        mapSaverLoader.load(testResource);
    }

}