package crawler;

import util.*;
import model.*;
import org.jsoup.Jsoup;

import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ThoiKyCrawler {
    public void crawl() {
        String baseUrl = "https://nguoikesu.com";
        String thoiKyUrl = "/dong-lich-su";
        ArrayList<ThoiKyModel> thoiKyList = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(baseUrl+thoiKyUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  Lấy các elements trong sidebar
        Elements liElements = doc.selectFirst("ul.mod-articlescategories.categories-module.mod-list").select("li");

        for (Element liElement : liElements) {
            thoiKyUrl = liElement.selectFirst("a").attr("href");
            try {
                doc = Jsoup.connect(baseUrl+thoiKyUrl).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(baseUrl+thoiKyUrl);  // Console log

            //  Get thoiKyName
            String thoiKyName = doc.selectFirst("h1").text();
            ThoiKyModel thoiKy = new ThoiKyModel(thoiKyName);

            //  Get thoiKyCode
            String thoiKyCode = thoiKyUrl.substring(thoiKyUrl.lastIndexOf("/") + 1);
            thoiKy.setCode(thoiKyCode);

            //  Get description
            Elements dElements = doc.selectFirst("div.category-desc.clearfix").select("p");
            ArrayList<String> description = new ArrayList<>();
            for (Element dElement : dElements) {
                description.add(dElement.wholeText());
            }
            thoiKy.setDescription(description);

            //  Get all links related to thoi ky
            ArrayList<String> links = new ArrayList<>();
            String nextPageUrl = "";
            while (nextPageUrl != null) {
                Elements h2Elements = doc.select("div.page-header h2");
                for (Element h2Element : h2Elements) {
                    links.add(h2Element.selectFirst("a").attr("href"));
                }
                Element nextPage = doc.selectFirst("a[aria-label=Đi tới tiếp tục trang].page-link");
                if (nextPage != null) nextPageUrl=nextPage.attr("href");
                else {
                    nextPageUrl = null;
                    continue;
                }
                try {
                    doc = Jsoup.connect(baseUrl+nextPageUrl).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } 
            }
            
            Set<String> nhanVatLienQuan = new HashSet<>();
            Set<String> diaDanhLienQuan = new HashSet<>();
            //  Traverse all links to find relative figures
            for (String link : links) {
                System.out.println(baseUrl+link);
                ArrayList<String> hrefs = getRelatives(baseUrl+link);

                for (String href : hrefs) {
                    String code = href.substring(href.lastIndexOf("/") + 1);
                    if (href.contains("nhan-vat")) {
                        nhanVatLienQuan.add(code);
                    } else if (href.contains("dia-danh")) {
                        diaDanhLienQuan.add(code);
                    }
                }
            }
            
            thoiKy.setNhanVatLienQuan(nhanVatLienQuan);
            thoiKy.setDiaDanhLienQuan(diaDanhLienQuan);
            thoiKyList.add(thoiKy);
        }

        // Write to JSON file
        Utility.writer(Config.THOI_KY_FILENAME, thoiKyList);

    }

    // Lay thong tin nhan vat tu trang gom nhieu trang con
    public static ArrayList<String> getRelatives(String url) {
        ArrayList<String> hrefs = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  Find in article body
        Element articleBody = doc.selectFirst("div[itemprop=articleBody]");
        if (articleBody != null) {
            Elements aTags = doc.selectFirst("div[itemprop=articleBody]").select("a");
            for (Element aTag : aTags) {
                String href = aTag.attr("href");
                if (href.isEmpty() || href.startsWith("#")) continue;
                hrefs.add(href);
            }
        }

        //  Find in article sidebar
        Element isNhanVat = doc.selectFirst("div.caption > h3 > a");
        if (isNhanVat != null) 
            hrefs.add(isNhanVat.attr("href"));
        return hrefs;
    }

    public void outputTxt () {
        ArrayList<ThoiKyModel> thoiKyList = Utility.loader(Config.THOI_KY_FILENAME, new TypeToken<ArrayList<ThoiKyModel>>() {});
        StringBuilder stringBuilder = new StringBuilder();
        for (ThoiKyModel thoiKy : thoiKyList) {
            stringBuilder.append(Utility.removeAccentsAndToLowercase(thoiKy.getName())).append(thoiKy.toHtml()).append("\n");
        }
        String filePathOutput = "txtOutput/ThoiKy.txt";
        String outputTxt = stringBuilder.toString();
        try (FileWriter writer = new FileWriter(filePathOutput)) {
            writer.write(outputTxt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Testing
    public static void main(String[] args) {
        ThoiKyCrawler test = new ThoiKyCrawler();
        // test.crawl();
        test.outputTxt();
    }

}
