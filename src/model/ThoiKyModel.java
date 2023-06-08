package model;

import java.util.ArrayList;
import java.util.Set;

public class ThoiKyModel extends Model{
    private String code;
    private ArrayList<String> description;
    private Set<String> nhanVatLienQuan;
    private Set<String> diaDanhLienQuan;

    public ThoiKyModel(String ten) {
        super(ten);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public Set<String> getNhanVatLienQuan() {
        return nhanVatLienQuan;
    }

    public void setNhanVatLienQuan(Set<String> nhanVatLienQuan) {
        this.nhanVatLienQuan = nhanVatLienQuan;
    }

    public Set<String> getDiaDanhLienQuan() {
        return diaDanhLienQuan;
    }

    public void setDiaDanhLienQuan(Set<String> diaDanhLienQuan) {
        this.diaDanhLienQuan = diaDanhLienQuan;
    }

    @Override
    public String toString() {
        return "\n{ \"Tên thời kỳ\":\"" + super.getName() + "\", "
                + "\n\"Miêu tả\":\"" + this.description + "\", "
                + "\n\"Nhân vật liên quan code\":\"" + this.nhanVatLienQuan + "\" }" + "\n";
    }
}