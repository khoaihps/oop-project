package model;

import java.util.Objects;

public class Relative {
    String text;
    String href;
    public Relative(String text, String href) {
        this.text = text;
        this.href = href;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Relative relative = (Relative) obj;
        return Objects.equals(text, relative.text) && Objects.equals(href, relative.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, href);
    }

    public String toString() {
        String outputString = "   text= \"" + text + '\"' +
                            "\n   href= \"" + href + '\"' ;
        return outputString;
    }
}
