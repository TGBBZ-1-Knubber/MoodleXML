package de.knubber.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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

    @XmlElement(name = "generalfeedback")
    private Feedback generalFeedback;

    @XmlAttribute(name = "defaultgrade")
    private Double defaultGrade;

    @XmlAttribute(name = "penalty")
    private Double penalty;

    @XmlAttribute(name = "hidden")
    private int hidden;

    @XmlAttribute(name = "idnumber")
    private int idNumber;

    @XmlAttribute(name = "single")
    private boolean single;

    @XmlAttribute(name = "shuffleanswers")
    private boolean shuffleAnswers;

    @XmlAttribute(name = "answernumbering")
    private String answerNumbering;

    @XmlAttribute(name = "showstandardinstruction")
    private int showStandardInstruction;

    @XmlElement(name = "correctfeedback")
    private Feedback correctFeedback;

    @XmlElement(name = "partiallycorrectfeedback")
    private Feedback partiallyCorrectFeedback;

    @XmlElement(name = "incorrectfeedback")
    private Feedback incorrectFeedback;

    @XmlAttribute(name = "shownumcorrect")
    private boolean showNumCorrect;

    @XmlElement(name = "answer")
    private Answer answers;

    @XmlAttribute(name = "tags")
    private Tag tags;

}

