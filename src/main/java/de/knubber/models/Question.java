package de.knubber.models;

import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Question {
    @XmlAttribute(name = "type")
    private String type;

    @XmlElement(name = "name")
    private Name name;

    @XmlElement(name = "questiontext")
    private QuestionText questionText;

    @XmlElement(name = "answer")
    private List<Answer> answers;

    @XmlElement(name = "subquestion")
    private List<SubQuestion> subQuestions;

    @XmlElement(name = "correctfeedback")
    private Feedback correctFeedback;

    @XmlElement(name = "incorrectfeedback")
    private Feedback incorrectFeedback;

    @XmlElement(name = "partiallycorrectfeedback")
    private Feedback partiallyCorrectFeedback;

    @XmlElement(name = "tags")
    private Tags tags;
}
