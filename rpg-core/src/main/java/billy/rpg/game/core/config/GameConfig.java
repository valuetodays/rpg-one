package billy.rpg.game.core.config;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class GameConfig {
    /**
     * 显示fps的字体的名称、样式、大小、颜色
     */
    private String fpsFontName;
    private int fpsFontStyle;
    private int fpsFontSize;
    private Font fpsFont;
    private Color fpsFontColor;
    /**
     * 显示地图名称的字体的名称、样式、大小、颜色
     */
    private String mapNameFontName;
    private int mapNameFontStyle;
    private int mapNameFontSize;
    private Font mapNameFont;
    private Color mapNameFontColor;
    /**
     * 对话框中字体的名称、样式、大小、颜色
     */
    private String dialogFontName;
    private int dialogFontStyle;
    private int dialogFontSize;
    private Font dialogFont;
    private Color dialogFontColor;
    /**
     * 对话框上方的角色名称字体的名称、样式、大小、颜色
     */
    private String dialogRoleNameFontName;
    private int dialogRoleNameFontStyle;
    private int dialogRoleNameFontSize;
    private Font dialogRoleNameFont;
    private Color dialogRoleNameFontColor;
    /**
     * 战斗伤害字体的名称、样式、大小、颜色
     */
    private String damageFontName;
    private int damageFontStyle;
    private int damageFontSize;
    private Font damageFont;
    private Color damageCommonFontColor; // 普攻伤害颜色
    private Color damageSkillFontColor; // 技能伤害颜色

    /** 对话框中每行的最大字数   */
    private int wordNumPerLineInDialog = 20;

    public void init() {
        Map<String, String> map = ParamHolder.getMap();
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            String fieldName = declaredField.getName();
            try {
                Object o = getValue(type, map.get(fieldName));
                declaredField.setAccessible(true);
                declaredField.set(this, o);
            } catch (Exception e) {
                System.err.println("error when " + fieldName);
                e.printStackTrace();
            }
        }

        fpsFont = new Font(fpsFontName, fpsFontStyle, fpsFontSize);
        mapNameFont = new Font(mapNameFontName, mapNameFontStyle, mapNameFontSize);
        dialogFont = new Font(dialogFontName, dialogFontStyle, dialogFontSize);
        dialogRoleNameFont = new Font(dialogRoleNameFontName, dialogRoleNameFontStyle, dialogRoleNameFontSize);
        damageFont = new Font(damageFontName, damageFontStyle, damageFontSize);
    }

    private Object getValue(Class<?> type, String stringValue) {
        if (Integer.class == type || int.class == type) {
            return Integer.valueOf(StringUtils.trimToEmpty(stringValue));
        }
        if (String.class == type) {
            return stringValue;
        }
        if (Color.class == type) {
            String[] values = stringValue.split(",");
            if (values.length == 1) {
                return getColor(values[0]);
            } if (values.length == 3) {
                return new Color(
                        Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2])
                );
            }
        }
        if (Font.class == type) {
            return null;
        }

        throw new RuntimeException("type not support: " + type.getName());
    }

    /**
     * 根据颜色名称获取颜色对象 （可以将colorMap缓存）
     * @param name 颜色名称
     */
    private static Color getColor(final String name) {
        Map<String, Color> colorMap = new HashMap<>();
        Class<Color> colorClass = Color.class;
        Field[] fields = colorClass.getFields();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC && field.getType() == colorClass) {
                try {
                    colorMap.put(field.getName(), (Color) field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return colorMap.get(name);
    }


    ////////////////////////////////////////////
    // get/set
    ////////////////////////////////////////////


    public String getFpsFontName() {
        return fpsFontName;
    }

    public void setFpsFontName(String fpsFontName) {
        this.fpsFontName = fpsFontName;
    }

    public int getFpsFontStyle() {
        return fpsFontStyle;
    }

    public void setFpsFontStyle(int fpsFontStyle) {
        this.fpsFontStyle = fpsFontStyle;
    }

    public int getFpsFontSize() {
        return fpsFontSize;
    }

    public void setFpsFontSize(int fpsFontSize) {
        this.fpsFontSize = fpsFontSize;
    }

    public Color getFpsFontColor() {
        return fpsFontColor;
    }

    public void setFpsFontColor(Color fpsFontColor) {
        this.fpsFontColor = fpsFontColor;
    }

    public String getMapNameFontName() {
        return mapNameFontName;
    }

    public void setMapNameFontName(String mapNameFontName) {
        this.mapNameFontName = mapNameFontName;
    }

    public int getMapNameFontStyle() {
        return mapNameFontStyle;
    }

    public void setMapNameFontStyle(int mapNameFontStyle) {
        this.mapNameFontStyle = mapNameFontStyle;
    }

    public int getMapNameFontSize() {
        return mapNameFontSize;
    }

    public void setMapNameFontSize(int mapNameFontSize) {
        this.mapNameFontSize = mapNameFontSize;
    }

    public Color getMapNameFontColor() {
        return mapNameFontColor;
    }

    public void setMapNameFontColor(Color mapNameFontColor) {
        this.mapNameFontColor = mapNameFontColor;
    }

    public String getDialogFontName() {
        return dialogFontName;
    }

    public void setDialogFontName(String dialogFontName) {
        this.dialogFontName = dialogFontName;
    }

    public int getDialogFontStyle() {
        return dialogFontStyle;
    }

    public void setDialogFontStyle(int dialogFontStyle) {
        this.dialogFontStyle = dialogFontStyle;
    }

    public int getDialogFontSize() {
        return dialogFontSize;
    }

    public void setDialogFontSize(int dialogFontSize) {
        this.dialogFontSize = dialogFontSize;
    }

    public Color getDialogFontColor() {
        return dialogFontColor;
    }

    public void setDialogFontColor(Color dialogFontColor) {
        this.dialogFontColor = dialogFontColor;
    }

    public String getDialogRoleNameFontName() {
        return dialogRoleNameFontName;
    }

    public void setDialogRoleNameFontName(String dialogRoleNameFontName) {
        this.dialogRoleNameFontName = dialogRoleNameFontName;
    }

    public int getDialogRoleNameFontStyle() {
        return dialogRoleNameFontStyle;
    }

    public void setDialogRoleNameFontStyle(int dialogRoleNameFontStyle) {
        this.dialogRoleNameFontStyle = dialogRoleNameFontStyle;
    }

    public int getDialogRoleNameFontSize() {
        return dialogRoleNameFontSize;
    }

    public void setDialogRoleNameFontSize(int dialogRoleNameFontSize) {
        this.dialogRoleNameFontSize = dialogRoleNameFontSize;
    }

    public Color getDialogRoleNameFontColor() {
        return dialogRoleNameFontColor;
    }

    public void setDialogRoleNameFontColor(Color dialogRoleNameFontColor) {
        this.dialogRoleNameFontColor = dialogRoleNameFontColor;
    }

    public String getDamageFontName() {
        return damageFontName;
    }

    public void setDamageFontName(String damageFontName) {
        this.damageFontName = damageFontName;
    }

    public int getDamageFontStyle() {
        return damageFontStyle;
    }

    public void setDamageFontStyle(int damageFontStyle) {
        this.damageFontStyle = damageFontStyle;
    }

    public int getDamageFontSize() {
        return damageFontSize;
    }

    public void setDamageFontSize(int damageFontSize) {
        this.damageFontSize = damageFontSize;
    }

    public int getWordNumPerLineInDialog() {
        return wordNumPerLineInDialog;
    }

    public void setWordNumPerLineInDialog(int wordNumPerLineInDialog) {
        this.wordNumPerLineInDialog = wordNumPerLineInDialog;
    }

    public Font getFpsFont() {
        return fpsFont;
    }

    public void setFpsFont(Font fpsFont) {
        this.fpsFont = fpsFont;
    }

    public Font getMapNameFont() {
        return mapNameFont;
    }

    public Font getDialogFont() {
        return dialogFont;
    }

    public Font getDialogRoleNameFont() {
        return dialogRoleNameFont;
    }

    public Font getDamageFont() {
        return damageFont;
    }

    public Color getDamageCommonFontColor() {
        return damageCommonFontColor;
    }

    public Color getDamageSkillFontColor() {
        return damageSkillFontColor;
    }
}
