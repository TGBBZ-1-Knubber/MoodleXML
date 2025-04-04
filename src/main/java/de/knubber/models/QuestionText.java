package de.knubber.models;

import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class QuestionText {
    @XmlElement(name = "text")
    private String text;
    @XmlElement(name = "file")
    private FileData file;
}
