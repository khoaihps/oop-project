package crawler;

import util.*;
import model.*;

import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.util.Collections;
import java.util.Comparator;


public class NhanVatCrawler {

    public void crawlNguoiKeSu() {
        String baseUrl = "https://nguoikesu.com";
        String nhanVatUrl = "/nhan-vat/an-duong-vuong";
        Document doc;

        ArrayList<NhanVatModel> nhanVatList = new ArrayList<>();

        while (nhanVatUrl != null) {
            System.out.println(baseUrl + nhanVatUrl);
            try {
                doc =  Jsoup.connect(baseUrl+nhanVatUrl).get();
            } catch (IOException e ){
                throw new RuntimeException(e);
            }
            
            // Collecting data
            String name = doc.selectFirst("div.page-header > h2").text();
            NhanVatModel nhanVat = new NhanVatModel(name);

            int lastSlashIndex = nhanVatUrl.lastIndexOf("/");   // Get the text after last slash of url as nhanVatCode
            String nhanVatCode = nhanVatUrl.substring(lastSlashIndex + 1);
            nhanVat.setCode(nhanVatCode);

            // Get table
            
            Element table = doc.selectFirst("table.infobox"); // select the table element with class "infobox"
            if (table != null) {
                // Map<String, ArrayList<String>> infoMap = new HashMap<String, ArrayList<String>>();
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    String label = row.select("th[scope=row], th[colspan=2]").text();
                    if (label.isEmpty()) {
                        continue; 
                    } 

                    ArrayList<String> info = new ArrayList<String>();
                    info.add(label);   // Set info[0] as label

                    //  Add information to info[1..]
                    Elements tabElements = row.select("table");
                    if (tabElements.size() > 0) {
                        Elements liElements = row.select("td li");
                        if (liElements.size() > 0) {
                            // if there are li elements, get their text and concatenate with line breaks
                            for (Element li : liElements) {
                                info.add(li.text());
                            }
                        } else {
                            Elements tdElements = tabElements.select("tr td");
                            if (tdElements.size() > 0) info.add(tdElements.text());
                        }
                    } else {
                        Elements brElements = row.select("td br");
                        if (brElements.size() > 0) {
                            row = row.select("td").get(row.select("td").size()-1);;
                            String tdContent = row.select("td").html();
                            String lines[] = tdContent.split("<br>");
                            for (String line : lines) {
                                info.add(Jsoup.parse(line).text());
                            } 
                        } else {
                            Element tdElement = row.selectFirst("td");
                            if (tdElement != null) info.add(tdElement.text());
                        }
                    }
                    
                    nhanVat.getInfobox().add(info);
                }     
            }
            

            //  Scrape description
            ArrayList<String> info = new ArrayList<String>();
            Element firstPTag = doc.selectFirst("p");
            if (firstPTag != null)  {
                String text = firstPTag.wholeText();
                info.add(text);
                Element nextPTag = firstPTag.nextElementSibling();
                while (nextPTag != null && !nextPTag.tagName().equals("h2")) {
                    text = nextPTag.wholeText();
                    if (!text.isEmpty()) info.add(text);
                    nextPTag = nextPTag.nextElementSibling();
                }
                nhanVat.setDescription(info);
            }

            //  Search for relative figures
            Set<String> nhanVatLienQuan = new HashSet<>();
            Set<String> diaDanhLienQuan = new HashSet<>();
            Set<String> thoiKyLienQuan = new HashSet<>();;
            Elements aTags = doc.select("div[itemprop=articleBody].com-content-article__body a");
            for (Element aTag : aTags) {
                String href = aTag.attr("href");
                if (href.isEmpty() || href.startsWith("#")) continue;
                String code = href.substring(href.lastIndexOf("/") + 1);
                if (href.contains("nhan-vat")) {
                    nhanVatLienQuan.add(code);
                } else if (href.contains("dia-danh")) {
                    diaDanhLienQuan.add(code);
                } else if (href.contains("dong-lich-su")) {
                    thoiKyLienQuan.add(code);
                }
            }
            nhanVat.setNhanVatLienQuan(nhanVatLienQuan);
            nhanVat.setDiaDanhLienQuan(diaDanhLienQuan);
            nhanVat.setThoiKyLienQuan(thoiKyLienQuan);

            // Add scapred nhanvat to list
            nhanVatList.add(nhanVat);

            // visit next page
            Element nextPage = doc.selectFirst("a.btn.btn-sm.btn-secondary.next");
            if (nextPage != null) {
                nhanVatUrl = nextPage.attr("href");
            } else {
                nhanVatUrl = null;
                System.out.println("Next page link not found.");
            }
        }

        //  Sorting the list by code
        Collections.sort(nhanVatList, new Comparator<NhanVatModel>() {
            public int compare(NhanVatModel nv1, NhanVatModel nv2) {
                return nv1.getCode().compareTo(nv2.getCode());
            }
        });
        
        // Write to JSON file
        Utility.writer(Config.NHAN_VAT_FILENAME, nhanVatList);
    }

    public void crawlWiki()  {

        //  Input from JSON back to Objects
        ArrayList<NhanVatModel> nhanVatList = Utility.loader(Config.NHAN_VAT_FILENAME, new TypeToken<ArrayList<NhanVatModel>>() {});
        
        String targetCode = "an-duong-vuong";
        NhanVatModel targetNhanVat = null;

        for (NhanVatModel nhanVat : nhanVatList) {
            if (nhanVat.getCode().equals(targetCode)) {
                targetNhanVat = nhanVat;
                break; // Exit the loop once the target is found
            }
        }
        String html = targetNhanVat.toHtml();
        String filePathOutput = "./htmlOutput/NhanVat.html";
        try (FileWriter writer = new FileWriter(filePathOutput)) {
            // Write the JSON string to the file
            writer.write(html);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // String baseUrl = "https://vi.wikipedia.org/wiki/";
        // String nhanVatUrl = "";
        // Document doc;


        // for (NhanVatModel nhanVat : nhanVatList) {
        //     nhanVatUrl = nhanVat.getName().replace(" ", "_");
        //     try {
        //         doc =  Jsoup.connect(baseUrl+nhanVatUrl).get();
        //     } catch (IOException e ){
        //         throw new RuntimeException(e);
        //     }
        //     Element table = doc.selectFirst("div.infobox:not([id])");
        //     if (table != null) {
        //         System.out.println("HTML contains infobox without id attribute");
        //     } else {
        //         System.out.println("HTML does not contain infobox without id attribute");
        //     }
        // }
    }

    public void outputTxt () {
        ArrayList<NhanVatModel> nhanVatList = Utility.loader(Config.NHAN_VAT_FILENAME, new TypeToken<ArrayList<NhanVatModel>>() {});
        StringBuilder stringBuilder = new StringBuilder();
        for (NhanVatModel nhanVat : nhanVatList) {
            stringBuilder.append(Utility.removeAccentsAndToLowercase(nhanVat.getName())).append(nhanVat.toHtml()).append("\n");
        }
        String filePathOutput = "./txtOutput/NhanVat.txt";
        String outputTxt = stringBuilder.toString();
        try (FileWriter writer = new FileWriter(filePathOutput)) {
            // Write the JSON string to the file
            writer.write(outputTxt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NhanVatCrawler test = new NhanVatCrawler();
        // test.crawlNguoiKeSu();
        // test.crawlWiki();
        test.outputTxt();
    }
}
