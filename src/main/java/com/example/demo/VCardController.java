package com.example.demo;

import ezvcard.VCard;
import ezvcard.property.StructuredName;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class VCardController {

    ArrayList<VCardData> results = new ArrayList<>();

    @RequestMapping("")
    public String index()
    {
        return "search";
    }

    @RequestMapping("/search")
    public String cardList(@RequestParam(defaultValue = "") String name, final Model model) {
        String SEARCH_URL = "https://adm.edu.p.lodz.pl/user/";
        String USERS = "users.php";
        String SEARCH_PARAM = "?search=";
        String url = SEARCH_URL + USERS + SEARCH_PARAM + name;

        try {
            results.clear();
            Document HtmlDocument = Jsoup.connect(url).get();
            Elements elements = HtmlDocument.getElementsByClass("user-info");
            for (Element element : elements) {
                VCardData result = new VCardData();
                result.setFullName(element.selectFirst("h3").text());
                result.setOrganizationUnit(element.selectFirst(".item-content").text());
                result.setTitle(element.selectFirst("h4").text());
                System.out.println(result.getFullName());
                results.add(result);
            }
            model.addAttribute("resultsList", results);
            model.addAttribute("name", name);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return "found";
    }

    @GetMapping("/download/{fullName}")
    public void download(@PathVariable String fullName, HttpServletResponse response, Model model) {
        for (VCardData result : results) {
            if (result.getFullName().equals(fullName)) {
                VCard vcard = new VCard();
                StructuredName n = new StructuredName();
                n.setGiven(fullName.substring(0, fullName.indexOf(" ")));
                n.setFamily(fullName.substring(fullName.indexOf(" ")+1));
                n.getPrefixes().add(result.getTitle());
                vcard.setStructuredName(n);
                vcard.setOrganization(result.getOrganizationUnit());

                String vcardName = fullName + ".vcf";

                try {
                    vcard.write(new File(vcardName));
                    File fileToDownload = new File(vcardName);
                    InputStream inputStream = new FileInputStream(fileToDownload);
                    response.setContentType("application/force-download");
                    response.setHeader("Content-Disposition", "attachment; filename=" + vcardName);
                    IOUtils.copy(inputStream, response.getOutputStream());
                    response.flushBuffer();
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}