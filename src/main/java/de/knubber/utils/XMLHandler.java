package de.knubber.utils;

import de.knubber.models.Answer;
import de.knubber.models.Question;
import de.knubber.models.Quiz;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.bind.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
                questionNode.add(new DefaultMutableTreeNode("<html><b>Fragetext:</b> " + (q.getQuestionText() != null ? q.getQuestionText().getText() : "❌ Fragetext fehlt!") + "</html>"));

                if (q.getInfo() != null && q.getInfo().getText() != null) {
                    questionNode.add(new DefaultMutableTreeNode("<html><b>Info:</b> " + q.getInfo().getText() + "</html>"));
                }

                DefaultMutableTreeNode answersNode = new DefaultMutableTreeNode("Antworten");
                List<Answer> answers = q.getAnswers();
                if (answers != null && !answers.isEmpty()) {
                    for (Answer a : answers) {
                        String answerText = "<html>" + (a.getText() != null ? a.getText() : "❌ Antworttext fehlt!") + (a.getFraction() != null && a.getFraction() > 0 ? " ✅" : " ❌") + "</html>";
                        DefaultMutableTreeNode answerNode = new DefaultMutableTreeNode(answerText);
                        if (a.getFeedback() != null && a.getFeedback().getText() != null) {
                            answerNode.add(new DefaultMutableTreeNode("<html><i>Feedback:</i> " + a.getFeedback().getText() + "</html>"));
                        }
                        answersNode.add(answerNode);
                    }
                } else {
                    answersNode.add(new DefaultMutableTreeNode("❌ Keine Antworten vorhanden!"));
                }
                questionNode.add(answersNode);

                root.add(questionNode);
            }

            JTree tree = new JTree(new DefaultTreeModel(root));
            tree.setRootVisible(true);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();

            JFrame frame = new JFrame("Quiz Fragen");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);

            JScrollPane scrollPane = new JScrollPane(tree);
            frame.add(scrollPane, BorderLayout.CENTER);

            JButton exportButton = new JButton("Exportieren");
            exportButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Speichern als XML");
                    fileChooser.setSelectedFile(new File("quiz.xml")); // Standardname setzen
                    int result = fileChooser.showSaveDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();

                        if (!file.getName().toLowerCase().endsWith(".xml")) {
                            file = new File(file.getAbsolutePath() + ".xml");
                        }

                        try {
                            JAXBContext context = JAXBContext.newInstance(Quiz.class);
                            Marshaller marshaller = context.createMarshaller();
                            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                            marshaller.marshal(quiz, file);
                            JOptionPane.showMessageDialog(frame, "Die Datei wurde erfolgreich exportiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                        } catch (JAXBException ex) {
                            JOptionPane.showMessageDialog(frame, "Fehler beim Exportieren der Datei: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(exportButton);
            frame.add(buttonPanel, BorderLayout.NORTH);

            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        frame.dispose();
                    }
                }
            });

            frame.setVisible(true);

        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Einlesen der XML-Datei: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
