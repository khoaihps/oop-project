package crawler;

import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public void crawl() {
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
            
            // Collect data
            String name = doc.select("h2[itemprop=headline]").text();
            NhanVatModel nhanVat = new NhanVatModel(name);
            nhanVat.setCode(nhanVatUrl);

            // Get table
            ArrayList<String> info = new ArrayList<String>();
            Element table = doc.selectFirst("table.infobox"); // select the table element with class "infobox"
            if (table != null) {
                // Map<String, ArrayList<String>> infoMap = new HashMap<String, ArrayList<String>>();
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    String label = row.select("th[scope=row]").text();
                    if (label.isEmpty()) {
                        continue; 
                    } 

                    info = new ArrayList<>();
                    Elements liElements = row.select("td li");
                    if (liElements.size() > 0) {
                        // if there are li elements, get their text and concatenate with line breaks
                        for (Element li : liElements) {
                            info.add(li.text());
                        }
                    } else {
                        // otherwise, get the text of the td element
                        Elements brElements = row.select("td br");
                        if (brElements.size() > 0) {
                            row = row.select("td").get(row.select("td").size()-1);;
                            String tdContent = row.select("td").html();
                            String lines[] = tdContent.split("<br>");
                            for (String line : lines) {
                                info.add(Jsoup.parse(line).text());
                            } 
                        } else info.add(row.select("td").text());
                    }
                    if (label.equals("Sinh")) {
                        nhanVat.setBirth(info);
                    } else
                    if (label.equals("Mất")) {
                        nhanVat.setDeath(info);
                    } else
                    nhanVat.table.add(new Pair(label,info));
                }
            }

            //  Scrape description
            info = new ArrayList<>();
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
            Set<Relative> hrefSet = new HashSet<>();
            Elements aTags = doc.select("div[itemprop=articleBody].com-content-article__body a");
            for (Element aTag : aTags) {
                String text = aTag.text();
                String href = aTag.attr("href");
                // Relative relative = new Relative(text,href);
                if (!href.isEmpty() && !href.startsWith("#")) {
                    hrefSet.add(new Relative(text,href));
                }
            }
            nhanVat.setRelatives(hrefSet);


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

        Collections.sort(nhanVatList, new Comparator<NhanVatModel>() {
            public int compare(NhanVatModel nv1, NhanVatModel nv2) {
                return nv1.getCode().compareTo(nv2.getCode());
            }
        });
        
        // Write to JSON file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(nhanVatList);

        // Specify the file path and name
        String filePath = "./database/NhanVat.json";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the JSON string to the file
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NhanVatCrawler test = new NhanVatCrawler();
        test.crawl();
    }
}
