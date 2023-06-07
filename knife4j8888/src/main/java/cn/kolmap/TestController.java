package cn.kolmap;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfTextArray;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;

/**
 * @author kxhan
 * @version 1.0
 * @date 2022/12/7 23:14
 */
@RestController
@Api(tags = "测试")
public class TestController {

    @GetMapping("/hello")
    @ApiOperation("查询测试")
    public String testHello(){
        return "hello world";
    }


    public static void main(String[] args) {
       pdfToSub("/Users/hankexun/Downloads/黄冈正茂1.pdf");
     //  pdfToSub("/Users/hankexun/Downloads/1684204122_610425199506161711.pdf");
    }

    public static void pdfToSub(String filePath) {
        Document document = null;
        PdfCopy copy = null;
        try {
            PdfReader reader = new PdfReader(filePath);
            //总页数
            int n = reader.getNumberOfPages();
            document = new Document(reader.getPageSize(1));


            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
            System.out.println(textFromPage);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
