package billy.rpg.common.constant;

/**
 * constant
 * @author liulei
 * @since 2017-04-28 09:57
 */
public interface MapEditorConstant {
    String MAPEDITOR_NAME = "地图编辑器MapEditor";

    int PANEL_WIDTH = 920;
    int PANEL_HEIGHT = 650;

    String HELP_MSG = "点击左侧tile区域选中tile块，再点击右侧地图区域绘制地图，在地图区域中：" +
            "\n- 地图层(前景及背景)：按w/s/a/d(或上/下/左/右)来左右行动地图" +
            "\n- npc层：shift+点击 --> 清理npc | 点击 --> 添加npc" +
            "\n- event层：254~255 --> 传送门 | 238-->宝箱(开) | 237 --> 宝箱(关)" +
            "\n- 行走层：点击右设置行走与不可行走 | shift+点击使所有行走层反转";

    int NPC_NONE = -1;
}
