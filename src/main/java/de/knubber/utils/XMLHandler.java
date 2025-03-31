package de.knubber.utils;

import de.knubber.models.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.bind.*;
import java.awt.*;
import java.io.*;
import java.util.Base64;
import java.util.List;

public class XMLHandler {

    public static void handleXML(InputStream inputStream) {
        try {
            JAXBContext context = JAXBContext.newInstance(Quiz.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Quiz quiz = (Quiz) unmarshaller.unmarshal(inputStream);

            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Quiz Fragen");

            for (Question q : quiz.getQuestions()) {
                String questionTitle = q.getName() != null ? q.getName().getText() : "❌ Name fehlt!";
                DefaultMutableTreeNode questionNode = new DefaultMutableTreeNode("<html><b>Fragename:</b> " + questionTitle + "</html>");
                questionNode.add(new DefaultMutableTreeNode("<html><b>Fragetyp:</b> " + q.getType() + "</html>"));

                String questionText = (q.getQuestionText() != null && q.getQuestionText().getText() != null)
                        ? q.getQuestionText().getText()
                        : "❌ Fragetext fehlt!";
                questionNode.add(new DefaultMutableTreeNode("<html><b>Fragetext:</b> " + questionText + "</html>"));

                // Falls eine Datei (Bild) existiert
                if (q.getQuestionText() != null && q.getQuestionText().getFile() != null) {
                    FileData fileData = q.getQuestionText().getFile();
                    ImageIcon imageIcon = decodeBase64ToImage(fileData.getContent());

                    if (imageIcon != null) {
                        DefaultMutableTreeNode imageNode = new DefaultMutableTreeNode(new ImageNode(imageIcon, fileData.getName()));
                        questionNode.add(imageNode);
                    }
                }

                root.add(questionNode);
            }

            JTree tree = new JTree(new DefaultTreeModel(root));
            tree.setRootVisible(true);
            tree.setCellRenderer(new ImageTreeCellRenderer());

            JFrame frame = new JFrame("Quiz Fragen");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new JScrollPane(tree), BorderLayout.CENTER);
            frame.setVisible(true);

        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der XML-Datei: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private static ImageIcon decodeBase64ToImage(String base64) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64);
            return new ImageIcon(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
