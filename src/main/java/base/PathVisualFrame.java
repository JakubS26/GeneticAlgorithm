package base;

import javax.swing.*;
import java.awt.*;

public class PathVisualFrame extends JFrame {
    public PathVisualFrame(MatrixEuclid matrix, Path path, String name) {
        this.setTitle(name);
        this.setBounds(500, 10, 1000, 1000);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.WHITE);
        PathVisual panel = new PathVisual(matrix, path);
        this.add(panel);
        panel.repaint();
        this.setVisible(true);
    }
}
