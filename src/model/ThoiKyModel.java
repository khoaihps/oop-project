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

    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        return jsonBuilder.toString();
    }

    public String toHtml() {
        StringBuilder htmlBuilder = new StringBuilder();

        // Start the HTML structure
        htmlBuilder.append("<html>");
        htmlBuilder.append("<head>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body contenteditable=\"true\">");
        htmlBuilder.append("<i>").append(getName()).append("</i>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        // htmlBuilder.append("<title>").append(getName()).append("</title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append("body { font-family:'lucida grande', tahoma, verdana, arial, sans-serif;font-size:14px; }");
        htmlBuilder.append("</style>");
        
        // Add the name as a heading
        // htmlBuilder.append("<h1>").append("NHÂN VẬT").append("</h1>");
        // htmlBuilder.append("<h1>").append(getName()).append("</h1>");

        // Add the code
        // htmlBuilder.append("<p><strong>Code:</strong> ").append(getCode()).append("</p>");

        // Add the infobox
        htmlBuilder.append("<h2>Thông tin thời kỳ</h2>");

        // Add the description
        for (String desc : getDescription()) {
            htmlBuilder.append("<p>").append(desc).append("</p>");
        }

        // Add the related figures
        htmlBuilder.append("<h2>Related Figures</h2>");
        htmlBuilder.append("<ul>");
        for (String figure : getNhanVatLienQuan()) {
            htmlBuilder.append("<li>").append(figure).append("</li>");
        }
        htmlBuilder.append("</ul>");

        // Add the related places
        htmlBuilder.append("<h2>Related Places</h2>");
        htmlBuilder.append("<ul>");
        for (String place : getDiaDanhLienQuan()) {
            htmlBuilder.append("<li>").append(place).append("</li>");
        }
        htmlBuilder.append("</ul>");

        // Close the HTML structure
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }

    @Override
    public String toString() {
        return "\n{ \"Tên thời kỳ\":\"" + super.getName() + "\", "
                + "\n\"Miêu tả\":\"" + this.description + "\", "
                + "\n\"Nhân vật liên quan code\":\"" + this.nhanVatLienQuan + "\" }" + "\n";
    }
}