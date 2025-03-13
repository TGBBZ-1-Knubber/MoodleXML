package de.knubber.utils;

import de.knubber.models.Question;
import de.knubber.models.Quiz;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;

public class XMLHandler {

    public static void handleXML(InputStream inputStream) {
        try {
            JAXBContext context = JAXBContext.newInstance(Quiz.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Quiz quiz = (Quiz) unmarshaller.unmarshal(inputStream);

            for (Question q : quiz.getQuestions()) {
                System.out.println("Fragetyp: " + q.getType());
                System.out.println("Fragename: " + (q.getName() != null ? q.getName().getText() : "❌ Name fehlt!"));
                System.out.println("Fragetext: " + (q.getQuestionText() != null ? q.getQuestionText().getText() : "❌ Fragetext fehlt!"));
                System.out.println("------------------------");
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }


    public static String convertQuizToXMLString(Quiz quiz) {
        try {
            // Erstelle ein JAXBContext für die Quiz-Klasse
            JAXBContext context = JAXBContext.newInstance(Quiz.class);

            // Erstelle einen Marshaller
            Marshaller marshaller = context.createMarshaller();

            // Optional: Formatierung für schöneres XML (lesbar)
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // StringWriter, um den XML-String zu speichern
            StringWriter stringWriter = new StringWriter();

            // Serialisiere das Quiz-Objekt in den StringWriter
            marshaller.marshal(quiz, stringWriter);

            // Gibt den generierten XML-String zurück
            return stringWriter.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
