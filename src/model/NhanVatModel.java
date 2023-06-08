package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NhanVatModel extends Model {
    private String code;
    private ArrayList<ArrayList<String>> infobox;
    private ArrayList<String> description;
    private Set<String> nhanVatLienQuan;
    private Set<String> diaDanhLienQuan;
    private Set<String> thoiKyLienQuan;


    public NhanVatModel(String name) {
        super(name);
        this.code = new String();
        this.infobox = new ArrayList<>();
        this.description = new ArrayList<>();
        this.nhanVatLienQuan = new HashSet<>();
        this.diaDanhLienQuan = new HashSet<>();
        this.thoiKyLienQuan = new HashSet<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<ArrayList<String>> getInfobox() {
        return infobox;
    }

    public void setInfobox(ArrayList<ArrayList<String>> infobox) {
        this.infobox = infobox;
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

    public Set<String> getThoiKyLienQuan() {
        return thoiKyLienQuan;
    }

    public void setThoiKyLienQuan(Set<String> thoiKyLienQuan) {
        this.thoiKyLienQuan = thoiKyLienQuan;
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
        htmlBuilder.append("table { font-family:'lucida grande', tahoma, verdana, arial, sans-serif;font-size:14px; }");
        htmlBuilder.append(".table-container { text-align: left; }");
        htmlBuilder.append("</style>");
        
        

        // Add the name as a heading
        // htmlBuilder.append("<h1>").append("NHÂN VẬT").append("</h1>");
        // htmlBuilder.append("<h1>").append(getName()).append("</h1>");

        // Add the code
        // htmlBuilder.append("<p><strong>Code:</strong> ").append(getCode()).append("</p>");

        // Add the infobox
        htmlBuilder.append("<h2>Thông tin nhân vật</h2>");
        htmlBuilder.append("<table class=\"table-container\">");
        for (ArrayList<String> info : getInfobox()) {
            htmlBuilder.append("<tr>");
            if (info.size() == 1) htmlBuilder.append("<th colspan=\"2\">");
            else htmlBuilder.append("<th scope=\"row\">");
            htmlBuilder.append(info.get(0)).append("</th>");
            htmlBuilder.append("<td>");
            for (int i = 1; i < info.size(); i++) {
                if (i > 1) htmlBuilder.append("<br>");
                htmlBuilder.append(info.get(i));
            }
            htmlBuilder.append("</td>");
            htmlBuilder.append("</tr>");
        }
        htmlBuilder.append("</table>");

        // Add the description
        htmlBuilder.append("<h2>Description</h2>");
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

        // Add the related time periods
        htmlBuilder.append("<h2>Related Time Periods</h2>");
        htmlBuilder.append("<ul>");
        for (String timePeriod : getThoiKyLienQuan()) {
            htmlBuilder.append("<li>").append(timePeriod).append("</li>");
        }
        htmlBuilder.append("</ul>");

        // Close the HTML structure
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }
}
