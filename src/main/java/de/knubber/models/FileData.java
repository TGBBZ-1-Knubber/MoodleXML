package de.knubber.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class FileData {
    private String name;
    private String path;
    private String encoding;
    private String content;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlAttribute
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @XmlValue
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

