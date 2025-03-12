package de.knubber.models;

import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Tags {
    @XmlElement(name = "tag")
    private List<Tag> tags;
}
