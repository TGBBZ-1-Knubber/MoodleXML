package de.knubber.models;

import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "quiz")
@XmlAccessorType(XmlAccessType.FIELD)
public class Quiz {
    @XmlElement(name = "question")
    private List<Question> questions;
}
