package model;

import java.util.Set;

public class DiaDanhModel extends Model{
    private String description;
    private String diaDanhCode;
    private Set<String> nhanVatLienQuan;
    public DiaDanhModel(String name) {
        super(name);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getNhanVatLienQuan() {
        return nhanVatLienQuan;
    }

    public void setNhanVatLienQuan(Set<String> nhanVatLienQuan) {
        this.nhanVatLienQuan = nhanVatLienQuan;
    }

    public String getDiaDanhCode() {
        return diaDanhCode;
    }

    public void setDiaDanhCode(String diaDanhCode) {
        this.diaDanhCode = diaDanhCode;
    }

    @Override
    public String toString() {
        return "\n{ \"Địa danh\":\"" + super.getName() + "\", "
                + "\n\"Code\":\"" + this.diaDanhCode + "\", "
                + "\n\"Miêu tả\":\"" + this.description + "\", "
                + "\n\"Nhân vật liên quan code\":\"" + this.nhanVatLienQuan + "\" }" + "\n";
    }
}
