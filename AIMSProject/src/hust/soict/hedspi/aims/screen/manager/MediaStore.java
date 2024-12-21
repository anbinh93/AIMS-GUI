package hust.soict.hedspi.aims.screen.manager;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.media.Playable;

public class MediaStore extends JPanel {

    private static final long serialVersionUID = 1L;

    // Extracted constant for currency symbol
    private static final String CURRENCY = " $";

    private Media media;

    public MediaStore(Media media) {
        this.media = media;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Adding components
        this.add(Box.createVerticalGlue());
        this.add(createTitleLabel());
        this.add(createCostLabel());
        this.add(createButtonPanel());
        this.add(Box.createVerticalGlue());

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // Extracted method to create title label
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel(media.getTitle());
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 15));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        return titleLabel;
    }

    // Extracted method to create cost label
    private JLabel createCostLabel() {
        JLabel costLabel = new JLabel(media.getCost() + CURRENCY);
        costLabel.setAlignmentX(CENTER_ALIGNMENT);
        return costLabel;
    }

    // Extracted method to create button panel
    private JPanel createButtonPanel() {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        if (media instanceof Playable) {
            JButton playButton = new JButton("Play");
            container.add(playButton);
        }

        return container;
    }
}