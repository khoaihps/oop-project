package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NhanVatModel extends Model {
    public String code;
    protected ArrayList<String> birth, death;
    public ArrayList<Pair> table;
    protected ArrayList<String> description;
    private Set<Relative> relatives;
    
    // private ArrayList<Relative> relatives;

    public NhanVatModel(String name) {
        super(name);
        this.code = new String();
        this.birth = new ArrayList<>();
        this.death = new ArrayList<>();
        this.table = new ArrayList<>();
        this.description = new ArrayList<>();
        this.relatives = new HashSet<>();
    }


    // public NhanVatModel() {
    //     this.code = new String;
    //     this.birth = new ArrayList<>()
    //     this.death = new ArrayList<>();
    //     this.table = new ArrayList<>();
    //     this.description = new ArrayList<>();
    //     this.relatives = new ArrayList<>();
    // }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<String> getBirth() {
        return birth;
    }

    public void setBirth(ArrayList<String> birth) {
        this.birth = birth;
    }

    public ArrayList<String> getDeath() {
        return death;
    }

    public void setDeath(ArrayList<String> death) {
        this.death = death;
    }

    public ArrayList<Pair> getTable() {
        return table;
    }

    public void setTable(ArrayList<Pair> table) {
        this.table = table;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public Set<Relative> getRelatives() {
        return relatives;
    }

    public void setRelatives(Set<Relative> relatives) {
        this.relatives = relatives;
    }

    public void addRelative(Relative relative){
        relatives.add(relative);
    }
}
