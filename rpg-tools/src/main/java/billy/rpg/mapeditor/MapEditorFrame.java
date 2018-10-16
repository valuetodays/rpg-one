package billy.rpg.mapeditor;

import billy.rpg.common.constant.MapEditorConstant;
import billy.rpg.resource.map.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * main frame to show the map editor
 *
 * @since 2017-04-28 09:56:29
 */
public class MapEditorFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MapEditorFrame.class);

    private MapEditorPanel mapEditorPanel;
    private EventNumDialog eventNumDialog;
    private NPCDialog npcDialog;
    private MapEditorFrame instance;
    private MapSaverLoader mapSaverLoader = new BinaryMapSaverLoader();

    public static void main(String[] args) {
        new MapEditorFrame();
    }

    public MapEditorFrame() {
        mapEditorPanel = new MapEditorPanel(this);
        this.setContentPane(mapEditorPanel);
        eventNumDialog = new EventNumDialog(this);
        npcDialog = new NPCDialog(this);

        String path = this.getClass().getClassLoader().getResource("").getPath() + "/map-editor.png";
        Image iconImage = Toolkit.getDefaultToolkit().getImage(path);
        setIconImage(iconImage);

        setTitle(MapEditorConstant.MAPEDITOR_NAME);
        setLocation(500, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        initMenuBar();

        pack();
        instance = this;
        LOG.info("MapEditor starts");
    }

    /**
     * 初始化菜单项
     */
    private void initMenuBar() {
        JMenuBar mb;
        JMenu menuFile, menuLayer, menuHelp;
        JMenuItem mItemFileOpen, mItemFileLoad, mItemFileSave, menuItemFileExit,
                menuItemLayer1, menuItemLayer2, menuItemLayer3, menuItemLayer4, menuItemLayer5,
                menuItemHelpAbout, menuItemHelpHelp;

        mb = new JMenuBar(); // 创建菜单栏MenuBar
        menuFile = new JMenu("File");
        menuLayer = new JMenu("Layer");
        menuHelp = new JMenu("Help");

        mItemFileOpen = new JMenuItem("Open");
        mItemFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = mapEditorPanel.getFileTileChooser();
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    mapEditorPanel.setTileImage(name);
                }
            }
        });
        menuFile.add(mItemFileOpen);
        mItemFileLoad = new JMenuItem("Load");
        mItemFileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("to load *.map");
                MapMetaData mapMetaData = gatherMapData();
                if (null != mapMetaData.getTileId()) {
                    int option = JOptionPane.showConfirmDialog(instance, "现在数据会丢失，确定要加载吗？");
                    if (JOptionPane.OK_OPTION != option) {
                        return ;
                    }
                }

                JFileChooser chooser = mapEditorPanel.getFileMapLoadChooser();
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    try {
                        mapMetaData = mapSaverLoader.load(name);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        throw new RuntimeException(e1);
                    }
                    getMapEditorPanel().getTileArea().setTileId(mapMetaData.getTileId());
                    getMapEditorPanel().setMapName(mapMetaData.getName());
                    getMapEditorPanel().setMapWidth(mapMetaData.getWidth());
                    getMapEditorPanel().setMapHeight(mapMetaData.getHeight());
                    MapAreaPanel mapArea = getMapEditorPanel().getMapArea();
                    mapArea.setLayers(mapMetaData.getLayers());
                    mapArea.setTileYheight(mapMetaData.getHeight());
                    mapArea.setTileXwidth(mapMetaData.getWidth());
                }
            }
        });
        menuFile.add(mItemFileLoad);
        mItemFileSave = new JMenuItem("Save");
        mItemFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = mapEditorPanel.getFileMapSaveChooser();
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = chooser.getSelectedFile();
                    String name = chooser.getName(selectedFile);
                    if (!name.endsWith(".map")){
                        name += ".map";
                    }
                    MapMetaData mapMetaData = gatherMapData();
                    try {
                        new BinaryMapSaverLoader().save(chooser.getCurrentDirectory() + File.separator + name,
                                mapMetaData);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        menuFile.add(mItemFileSave);
        menuFile.addSeparator(); // 加入分割线
        menuItemFileExit = new JMenuItem("Exit");
        menuItemFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(menuItemFileExit);
        mb.add(menuFile); // 菜单栏中加入“文件”菜单

        ButtonGroup layerGroup = new ButtonGroup();//设置单选组
        menuItemLayer1 = new JRadioButtonMenuItem("Layer1 (bg)");
        menuItemLayer1.doClick();
        menuItemLayer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer1 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(1);
            }
        });
        menuItemLayer2 = new JRadioButtonMenuItem("Layer2 (npc)");
        menuItemLayer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer2 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(2);
            }
        });
        menuItemLayer3 = new JRadioButtonMenuItem("Layer3 (fg)");
        menuItemLayer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer3 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(3);
            }
        });
        menuItemLayer4 = new JRadioButtonMenuItem("Layer4 (walk)");
        menuItemLayer4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer4 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(4);
            }
        });
        menuItemLayer5 = new JRadioButtonMenuItem("Layer5 (event)");
        menuItemLayer5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer5 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(5);
            }
        });
        layerGroup.add(menuItemLayer1);
        layerGroup.add(menuItemLayer2);
        layerGroup.add(menuItemLayer3);
        layerGroup.add(menuItemLayer4);
        layerGroup.add(menuItemLayer5);
        menuLayer.add(menuItemLayer1);
        menuLayer.add(menuItemLayer2);
        menuLayer.add(menuItemLayer3);
        menuLayer.add(menuItemLayer4);
        menuLayer.add(menuItemLayer5);
        menuLayer.addSeparator();

        mb.add(menuLayer);

        menuItemHelpAbout = new JMenuItem("About");
        menuItemHelpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(instance, "MapEditor for rpg-one\n\nby billy.\n\n2017-07");
            }
        });
        menuHelp.add(menuItemHelpAbout);
        menuItemHelpHelp = new JMenuItem("Help");
        menuItemHelpHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(instance, MapEditorConstant.HELP_MSG);
            }
        });
        menuHelp.add(menuItemHelpHelp);
        mb.add(menuHelp);

        // setMenuBar:将此窗体的菜单栏设置为指定的菜单栏。
        setJMenuBar(mb);
    }

    private MapMetaData gatherMapData() {
        String tileId = getMapEditorPanel().getTileArea().getTileId();
        String mapName = getMapEditorPanel().getMapName();
        MapAreaPanel mapArea = getMapEditorPanel().getMapArea();
        List<int[][]> layers = mapArea.getLayers();
        int height = mapArea.getTileYheight();
        int width = mapArea.getTileXwidth();
        MapMetaData mapMetaData = new MapMetaData();
        mapMetaData.setTileId(tileId);
        mapMetaData.setName(mapName);
        mapMetaData.setLayers(layers);
        mapMetaData.setHeight(height);
        mapMetaData.setWidth(width);

        return mapMetaData;
    }


    public MapEditorPanel getMapEditorPanel() {
        return mapEditorPanel;
    }

    public EventNumDialog getEventNumDialog() {
        return eventNumDialog;
    }

    public NPCDialog getNpcDialog() {
        return npcDialog;
    }


}

