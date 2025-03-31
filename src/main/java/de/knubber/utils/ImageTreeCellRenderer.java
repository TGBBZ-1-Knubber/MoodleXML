package de.knubber.utils;

import de.knubber.models.ImageNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

class ImageTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode) {
            Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();

            if (nodeObj instanceof ImageNode) {
                ImageNode imageNode = (ImageNode) nodeObj;
                label.setIcon(imageNode.getImageIcon());
                label.setText(imageNode.getName());
                label.setHorizontalTextPosition(JLabel.RIGHT);
            }
        }

        return label;
    }
}

