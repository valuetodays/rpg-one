package test.billy.animation;

import org.apache.commons.lang.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;

/**
 * Created by Administrator on 2017/7/8.
 */
public class FrameOne {
    private JTextField tfFrameNumber;
    private JTextField tfNumber;
    private JList list1;
    private JButton btnSave;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JPanel MainPanel;
    private JList list2;
    private JButton btnLoad;
    private JButton removeButton;
    private JButton addButton;

    public FrameOne() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAniToFile();
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //if (e.getValueIsAdjusting())
                //return;
                //System.out.println("Selected from " + e.getFirstIndex() + " to " + e.getLastIndex());
                int selectedIndex = list1.getSelectedIndex();
                Object selectedValue = list1.getSelectedValue();
                System.out.println("selected index = " + selectedIndex + ", selected value = " + selectedValue);

            }
        });
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("load from ani file");
            }
        });
    }



    private void saveAniToFile() {
        System.out.printf("save ani to file start");
        String tfNumberText = tfNumber.getText();
        if (StringUtils.isEmpty(tfNumberText)) {
            JOptionPane.showMessageDialog(null, "编号不能为空");
            return ;
        }
        if (!StringUtils.isNumeric(tfNumberText)) {
            JOptionPane.showMessageDialog(null, "编号只能是整数");
            return ;
        }

        String tfFrameNumberText = tfFrameNumber.getText();
        if (StringUtils.isEmpty(tfFrameNumberText)) {
            JOptionPane.showMessageDialog(null, "帧数不能为空");
            return ;
        }
        if (!StringUtils.isNumeric(tfFrameNumberText)) {
            JOptionPane.showMessageDialog(null, "帧数只能是整数");
            return ;
        }
        System.out.printf("save ani to file end");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrameOne");
        frame.setContentPane(new FrameOne().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
