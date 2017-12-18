package billy.rpg.rb2.graphics;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 13:35
 */
public class GraphicsHolder {
    private PanoramasHolder panoramasHolder = new PanoramasHolder();
    private CharacterHolder characterHolder = new CharacterHolder();

    public void init() {
        panoramasHolder.init();
        characterHolder.init();
    }

    public PanoramasHolder getPanoramasHolder() {
        return panoramasHolder;
    }

    public CharacterHolder getCharacterHolder() {
        return characterHolder;
    }
}
