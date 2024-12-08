package practize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/*
 * This Java source file was auto generated by running 'gradle buildInit --type java-library'
 * by 'rfrolow' at '2/19/15 1:22 PM' with Gradle 2.2.1
 *
 * @author rfrolow, @date 2/19/15 1:22 PM
 */
public class SwingDemo {
    public boolean someLibraryMethod() {
        return true;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleFrame frame = new SimpleFrame();

                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //frame.setUndecorated(true);
                //frame.setResizable(false);
                frame.setLocationByPlatform(true);
                frame.setTitle("My Title");
                Toolkit kit = Toolkit.getDefaultToolkit();
                Dimension screenSize = kit.getScreenSize();
                int screenWidth = screenSize.width;
                int screenHeight = screenSize.height;
                frame.setSize(screenWidth / 2, screenHeight / 2);
                frame.setVisible(true);
            }
        });
    }
}

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    private JPanel buttonPanel;

    public SimpleFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        buttonPanel = new JPanel();

        ColorAction yellowAction = new ColorAction("Yellow", new ImageIcon("green-ball.gif"), Color.YELLOW);
        ColorAction blueAction = new ColorAction("Blue", new ImageIcon("blue-ball.gif"), Color.BLUE);
        ColorAction greenAction = new ColorAction("Green", new ImageIcon("green-ball.gif"), Color.GREEN);

        buttonPanel.add(new JButton(yellowAction));
        buttonPanel.add(new JButton(blueAction));
        buttonPanel.add(new JButton(greenAction));

        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
        imap.put(KeyStroke.getKeyStroke("ctrl G"), "panel.green");

        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.yellow", yellowAction);
        amap.put("panel.blue", blueAction);
        amap.put("panel.green", greenAction);

        buttonPanel.add(new NotHelloWorldComponent());
        for(UIManager.LookAndFeelInfo i : UIManager.getInstalledLookAndFeels()) {
            themeButton(i.getName(), i.getClassName());
        }
        add(buttonPanel);
        pack();

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                if(true) {
                    System.exit(0);
                }
            }
        });
    }

    public void themeButton(String name, final String plaf) {
        JButton b = new JButton(name);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    UIManager.setLookAndFeel(plaf);
                    SwingUtilities.updateComponentTreeUI(SimpleFrame.this);
                } catch(Exception e) { e.printStackTrace(); }
            }
        });
        buttonPanel.add(b);
    }

    public class ColorAction extends AbstractAction {
        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue("color", c);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
        }

        public void actionPerformed(ActionEvent event) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }
    }
}

class NotHelloWorldComponent extends JComponent {
    public static final int MESSAGE_X = 75;
    public static final int MESSAGE_Y = 100;
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Font f = new Font("SansSerif", Font.BOLD, 18);
        g2.setFont(f);

        String message = "My Component string";
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(message, context);

        double paddingHoriz = (getWidth() - bounds.getWidth()) / 2;
        double paddingVert = (getHeight() - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = paddingVert + ascent;

        g2.drawString(message, (int) paddingHoriz, (int)baseY);

        g2.setPaint(Color.LIGHT_GRAY);
        Line2D.Double line = new Line2D.Double(paddingHoriz, baseY, paddingHoriz + bounds.getWidth(), baseY);
        g2.draw(line);
        Rectangle2D rect = new Rectangle2D.Double(paddingHoriz, paddingVert, bounds.getWidth(), bounds.getHeight());
        g2.draw(rect);
    }

    public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }

}