package base;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class PathVisual extends JPanel {
    private static final float dotRadius = 8;
    MatrixEuclid matrix;
    Path path;

    public PathVisual(MatrixEuclid matrix, Path path) {

        this.matrix = matrix;
        this.path = path;

        double max = 0;

        for(int i=0; i < matrix.size; i++) {

            if(matrix.x_coordinates[i] > max) {
                max = matrix.x_coordinates[i];
            }

            if(matrix.y_coordinates[i] > max) {
                max = matrix.y_coordinates[i];
            }

        }


        for(int i=0; i < matrix.size; i++) {

            matrix.x_coordinates[i] = matrix.x_coordinates[i]*1000.0/max;
            matrix.y_coordinates[i] = matrix.y_coordinates[i]*1000.0/max;

        }

    }


    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        for(int i = 0; i < matrix.size; i++) {

            float x = (float) matrix.x_coordinates[i];
            float y = (float) matrix.y_coordinates[i];

            Ellipse2D ellipse = new Ellipse2D.Float(x - dotRadius/2, y - dotRadius/2, dotRadius, dotRadius);
            g2d.fill(ellipse);
        }

        g2d.setColor(Color.RED);

        if(path == null) {
            return;
        }

        for(int i = 0; i < path.size; i++) {

            float x1 = (float) matrix.x_coordinates[path.vertices[i]-1];
            float y1 = (float) matrix.y_coordinates[path.vertices[i]-1];

            float x2 = (float) matrix.x_coordinates[path.vertices[(i+1)% path.size]-1];//i+1
            float y2 = (float) matrix.y_coordinates[path.vertices[(i+1)% path.size]-1];//i+1

            Line2D line = new Line2D.Float(x1, y1, x2, y2);
            g2d.draw(line);
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
