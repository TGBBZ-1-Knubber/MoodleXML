package de.knubber.models;

import javax.swing.*;

public class ImageNode {
    private final ImageIcon imageIcon;
    private final String name;

    public ImageNode(ImageIcon imageIcon, String name) {
        this.imageIcon = imageIcon;
        this.name = name;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public String getName() {
        return name;
    }
}

