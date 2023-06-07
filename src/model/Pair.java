package model;

import java.util.ArrayList;

public class Pair {
    public String label;
    public ArrayList<String> info;

    public Pair() {
        this.label = new String();
        this.info = new ArrayList<>();
    }

    public Pair(String label, ArrayList<String> info) {
        this.label = label;
        this.info = info;
    }
    
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    
    public ArrayList<String> getInfo() {
        return info;
    }
    public void setInfo(ArrayList<String> info) {
        this.info = info;
    }

    public String toString() {
        String outputString = new String();
        outputString += "   label = \"" + label + '\"';
        for (String text : info)  {
            outputString += "\n      info = \"" + text + '\"';
        }
        return outputString;
    }
}
