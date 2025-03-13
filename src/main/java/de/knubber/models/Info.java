package de.knubber.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Info {

    @XmlElement(name = "format")
    private String format;

    @XmlElement(name = "text")
    private String text;
}
