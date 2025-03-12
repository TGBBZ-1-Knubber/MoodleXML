package de.knubber.models;

import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer {
    @XmlAttribute(name = "fraction")
    private Double fraction;

    @XmlElement(name = "text")
    private String text;

    @XmlElement(name = "feedback")
    private Feedback feedback;
}
