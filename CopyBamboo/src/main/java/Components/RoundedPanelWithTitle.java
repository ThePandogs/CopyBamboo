package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class RoundedPanelWithTitle extends JPanel {

    int round = 5;

    public RoundedPanelWithTitle() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Título del Borde");
        titledBorder.setTitleColor(Color.BLACK);

        this.setBorder(titledBorder);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Obtener los límites del componente, teniendo en cuenta el borde
        Insets insets = this.getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        g.setColor(this.getBackground());
        g.fillRoundRect(x, y, width, height, round, round);
    }


    }