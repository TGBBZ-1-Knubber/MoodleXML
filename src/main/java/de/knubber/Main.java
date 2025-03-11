package de.knubber;

import de.knubber.models.Question;
import de.knubber.models.Quiz;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {
    public static void main(String[] args) {
        /*
        try {
            JAXBContext context = JAXBContext.newInstance(Quiz.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Quiz quiz = (Quiz) unmarshaller.unmarshal(Main.class.getClassLoader().getResourceAsStream("quiz.xml"));

            for (Question q : quiz.getQuestions()) {
                System.out.println("Fragetyp: " + q.getType());
                System.out.println("Fragename: " + (q.getName() != null ? q.getName().getText() : "❌ Name fehlt!"));
                System.out.println("Fragetext: " + (q.getQuestionText() != null ? q.getQuestionText().getText() : "❌ Fragetext fehlt!"));
                System.out.println("------------------------");
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
         */
    }
}