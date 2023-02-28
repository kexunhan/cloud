package cn.kolmap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class blog1 {
	public static void main(String[] args) throws Exception {  
		//测试添加图片
		String sourceFile="/Users/hankexun/Downloads/picture/测试模板.docx";

    	addStampImage(sourceFile);
	}
	
	public static void addStampImage(String sourceFile) {
		XWPFDocument doc;
		try {
			doc = new XWPFDocument(new FileInputStream(sourceFile));

			List<XWPFParagraph> paragraphs = doc.getParagraphs();
			for (XWPFParagraph paragraph : paragraphs) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String imgFile = "/Users/hankexun/Downloads/picture/1.jpg";

					FileInputStream is = new FileInputStream(imgFile);
					run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(100), Units.toEMU(100)); // 100x100 pixels
					is.close();

					FileOutputStream fos = new FileOutputStream(sourceFile);
					doc.write(fos);
					fos.close();

					doc.write(new FileOutputStream(sourceFile));
				}


			}



		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private static void insertCellStamp(XWPFTableCell cell) throws InvalidFormatException, IOException {//给带有要盖章字样的单元格 加上章的图片

		

		for(XWPFParagraph para :cell.getParagraphs()) {
			for (XWPFRun run : para.getRuns()) {
				String imgFile = "/Users/hankexun/Downloads/picture/1.jpg";

				FileInputStream is = new FileInputStream(imgFile);
				run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(100), Units.toEMU(100)); // 100x100 pixels
				is.close();
				run.setText("  ",0);
			}

			


		}
    }
}
