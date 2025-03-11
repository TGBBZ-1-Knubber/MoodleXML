package de.knubber.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

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
}

