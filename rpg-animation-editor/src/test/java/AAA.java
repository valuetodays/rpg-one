import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2017/7/8.
 */
public class AAA {
    /*public static void main(final String args[]) {
        String labels[] = { "A", "B", "C", "D", "E" };
        JFrame frame = new JFrame("Sizing Samples");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JList jlist1 = new JList();
        jlist1.setListData(labels);

        jlist1.setVisibleRowCount(4);
        JScrollPane scrollPane1 = new JScrollPane(jlist1);
        frame.add(scrollPane1, BorderLayout.NORTH);

        jlist1.setSelectedIndices(new int[]{1,2});

        frame.setSize(300, 350);
        frame.setVisible(true);
    }*/



   /* public static void main(String[] args) {
        JFrame frame = new JFrame("Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());

        for (int i = 1; i <= 5; i++) {
            contentPane.add(new JButton("Button  " + i));
        }

        frame.pack();
        frame.setVisible(true);
    }*/

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int horizontalGap = 20;
        int verticalGap = 10;
        Container contentPane = frame.getContentPane();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING, horizontalGap,
                verticalGap);
        contentPane.setLayout(flowLayout);
        frame.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        for (int i = 1; i <= 15; i++) {
            contentPane.add(new JButton("Button  " + i));
        }
        frame.pack();
        frame.setVisible(true);
    }*/

    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        // Add a button to each of the five areas of the BorderLayout
        container.add(new JButton("North"), BorderLayout.NORTH);
        container.add(new JButton("South"), BorderLayout.SOUTH);
        container.add(new JButton("East"), BorderLayout.EAST);
        container.add(new JButton("West"), BorderLayout.WEST);
        container.add(new JButton("Center"), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }*/

    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("CardLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        buttonPanel.add(nextButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        final JPanel cardPanel = new JPanel();
        final CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        for (int i = 1; i <= 5; i++) {
            JButton card = new JButton("Card " + i);
            card.setPreferredSize(new Dimension(200, 200));
            String cardName = "card" + 1;
            cardPanel.add(card, cardName);
        }
        contentPane.add(cardPanel, BorderLayout.CENTER);
        nextButton.addActionListener(e -> cardLayout.next(cardPanel));

        frame.pack();
        frame.setVisible(true);
    }*/
/*
    public static void main(String[] args) {
        JFrame frame = new JFrame("BoxLayout  Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JPanel hPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(hPanel, BoxLayout.X_AXIS);
        hPanel.setLayout(boxLayout);

        for (int i = 1; i <= 5; i++) {
            hPanel.add(new JButton("Button  " + i));
        }

        contentPane.add(hPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
*/

/*
    public static void main(String[] args) {
        JFrame frame = new JFrame("BoxLayout with Glue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        Box hBox = Box.createHorizontalBox();
        hBox.add(new JButton("First"));
        hBox.add(new JButton("Previous"));
        hBox.add(Box.createHorizontalGlue());
        hBox.add(new JButton("Next"));
        hBox.add(new JButton("Last"));

        contentPane.add(hBox, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
*/

   /* public static void main(String[] args) {
        JFrame frame = new JFrame("GridLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 0));

        for (int i = 1; i <= 9; i++) {
            buttonPanel.add(new JButton("Button  " + i));
        }

        contentPane.add(buttonPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }*/

/*
    public static void main(String[] args) {
        String title = "GridBagLayout";
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        for (int i = 1; i <= 9; i++) {
            contentPane.add(new JButton("Button  " + i));
        }
        frame.pack();
        frame.setVisible(true);
    }*/


    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("SpringLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        // Set the content pane's layout as SpringLayout
        SpringLayout springLayout = new SpringLayout();
        contentPane.setLayout(springLayout);

        // Add two JButtons to the content pane
        JButton b1 = new JButton("Button 1");
        JButton b2 = new JButton("Little Bigger   Button   2");
        contentPane.add(b1);
        contentPane.add(b2);

        frame.pack();
        frame.setVisible(true);
    }*/

/*
    public static void main(String[] args) {
        JFrame frame = new JFrame("GroupLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        GroupLayout groupLayout = new GroupLayout(contentPane);

        contentPane.setLayout(groupLayout);

        JLabel label = new JLabel("Label");
        JButton b2 = new JButton("Second Button");

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(label).addComponent(b2));

        groupLayout.setVerticalGroup(groupLayout
                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label)
                .addComponent(b2));

        frame.pack();
        frame.setVisible(true);
    }*/

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);

        JButton b1 = new JButton("Button");
        JButton b2 = new JButton("2");
        contentPane.add(b1);
        contentPane.add(b2);

        b1.setBounds(10, 10, 100, 20);
        b2.setBounds(120, 10, 150, 40);

        frame.setBounds(0, 0, 350, 100);
        frame.setVisible(true);
    }

}
