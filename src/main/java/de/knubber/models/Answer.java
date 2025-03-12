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
public class Answer {

    @XmlElement(name = "text")
    private String text;

    @XmlAttribute(name = "feedback")
    private Feedback feedback;

    @XmlElement(name = "fraction")
    private String fraction;

    @XmlElement(name = "format")
    private String format;
}
