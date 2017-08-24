package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-23 16:08
 */
public class JScrollPaneToTopAction implements ActionListener {
    JScrollPane scrollPane;
    public JScrollPaneToTopAction(JScrollPane scrollPane) {
        if (scrollPane == null) {
            throw new IllegalArgumentException(
                    "JScrollPaneToTopAction: null JScrollPane");
        }
        this.scrollPane = scrollPane;
    }
    public void actionPerformed(ActionEvent actionEvent) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        horizontalScrollBar.setValue(horizontalScrollBar.getMinimum());
    }


    public static void main(String args[]) {
        JFrame frame = new JFrame("Tabbed Pane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Label");
        label.setPreferredSize(new Dimension(1000, 1000));
        JScrollPane jScrollPane = new JScrollPane(label);

        JButton bn = new JButton("Move");

        bn.addActionListener(new JScrollPaneToTopAction(jScrollPane));

        frame.add(bn, BorderLayout.SOUTH);
        frame.add(jScrollPane, BorderLayout.CENTER);
        frame.setSize(400, 150);
        frame.setVisible(true);
    }
}
