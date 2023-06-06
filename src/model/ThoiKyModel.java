package model;

import java.util.Set;

public class ThoiKyModel extends Model{
    private String description;
    private Set<String> nhanVatLienQuan;
    public ThoiKyModel(String ten) {
        super(ten);
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

    @Override
    public String toString() {
        return "\n{ \"Tên thời kỳ\":\"" + super.getName() + "\", "
                + "\n\"Miêu tả\":\"" + this.description + "\", "
                + "\n\"Nhân vật liên quan code\":\"" + this.nhanVatLienQuan + "\" }" + "\n";
    }
}
