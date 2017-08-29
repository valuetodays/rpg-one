package billy.rpg.roleeditor;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-13 16:53
 */
public class RoleTypeItem {

    final int key;
    final String value;

    public RoleTypeItem(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
