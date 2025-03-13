package de.knubber.utils;

import de.knubber.models.*;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.List;

public class XMLHandler {

    public static void handleXML(InputStream inputStream) {
        try {
            JAXBContext context = JAXBContext.newInstance(Quiz.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Quiz quiz = (Quiz) unmarshaller.unmarshal(inputStream);

            // HTML-String für Anzeige
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body style='width: 90%; font-size: 16px;'>");

            for (Question q : quiz.getQuestions()) {
                sb.append("<h2>Fragetyp: ").append(q.getType()).append("</h2>");

                // Kategorie
                if (q.getCategory() != null && q.getCategory().getText() != null) {
                    sb.append("<p><b>Kategorie:</b> ").append(q.getCategory().getText()).append("</p>");
                }

                // Frage-Name
                sb.append("<p><b>Fragename:</b> ")
                        .append(q.getName() != null ? q.getName().getText() : "❌ Name fehlt!")
                        .append("</p>");

                // Frage-Text
                sb.append("<p><b>Fragetext:</b><br>")
                        .append(q.getQuestionText() != null ? q.getQuestionText().getText() : "❌ Fragetext fehlt!")
                        .append("</p>");

                // Info-Text
                if (q.getInfo() != null && q.getInfo().getText() != null) {
                    sb.append("<p><b>Info:</b> ").append(q.getInfo().getText()).append("</p>");
                }

                // Antworten
                List<Answer> answers = q.getAnswers();
                if (answers != null && !answers.isEmpty()) {
                    sb.append("<p><b>Antworten:</b></p><ul>");
                    for (Answer a : answers) {
                        sb.append("<li>")
                                .append(a.getText() != null ? a.getText() : "❌ Antworttext fehlt!")
                                .append(a.getFraction() != null && a.getFraction() > 0 ? " ✅" : " ❌");

                        if (a.getFeedback() != null && a.getFeedback().getText() != null) {
                            sb.append("<br><i>Feedback: ").append(a.getFeedback().getText()).append("</i>");
                        }

                        sb.append("</li>");
                    }
                    sb.append("</ul>");
                } else {
                    sb.append("<p><b>Antworten:</b> ❌ Keine Antworten vorhanden!</p>");
                }

                // Subfragen
                List<SubQuestion> subQuestions = q.getSubQuestions();
                if (subQuestions != null && !subQuestions.isEmpty()) {
                    sb.append("<p><b>Subfragen:</b></p><ul>");
                    for (SubQuestion sq : subQuestions) {
                        sb.append("<li><b>Frage:</b> ")
                                .append(sq.getText() != null ? sq.getText() : "❌ Fehlende Subfrage")
                                .append("<br><b>Antwort:</b> ")
                                .append(sq.getAnswer() != null ? sq.getAnswer() : "❌ Fehlende Antwort")
                                .append("</li>");
                    }
                    sb.append("</ul>");
                }

                // Feedback
                if (q.getCorrectFeedback() != null && q.getCorrectFeedback().getText() != null) {
                    sb.append("<p><b>Feedback (korrekt):</b> ").append(q.getCorrectFeedback().getText()).append("</p>");
                }
                if (q.getIncorrectFeedback() != null && q.getIncorrectFeedback().getText() != null) {
                    sb.append("<p><b>Feedback (falsch):</b> ").append(q.getIncorrectFeedback().getText()).append("</p>");
                }
                if (q.getPartiallyCorrectFeedback() != null && q.getPartiallyCorrectFeedback().getText() != null) {
                    sb.append("<p><b>Feedback (teilweise korrekt):</b> ").append(q.getPartiallyCorrectFeedback().getText()).append("</p>");
                }

                // Tags
                if (q.getTags() != null && q.getTags().getTags() != null && !q.getTags().getTags().isEmpty()) {
                    sb.append("<p><b>Tags:</b> ");
                    for (Tag tag : q.getTags().getTags()) {
                        sb.append(tag.getText()).append(", ");
                    }
                    sb.setLength(sb.length() - 2);
                    sb.append("</p>");
                }

                sb.append("<hr>");
            }

            sb.append("</body></html>");

            // Bildschirmgröße ermitteln
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = (int) screenSize.getWidth();
            int height = (int) screenSize.getHeight();

            // Fenster erstellen
            JFrame frame = new JFrame("Quiz Fragen (Vollbild)");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null); // Zentrieren

            // Label mit HTML-Inhalt
            JLabel label = new JLabel(sb.toString());
            label.setVerticalAlignment(SwingConstants.TOP);

            // Scrollpane für lange Inhalte
            JScrollPane scrollPane = new JScrollPane(label);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Schließen mit ESC-Taste
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
