package easy.excel;

import com.alibaba.excel.EasyExcel;
import easy.excel.converter.ImageConverter;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-28 11:37
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class EasyExcelTest {


    public static void main(String[] args) {
//
//        List<Model> dataList = EasyExcel.read("/Users/linzhou/Documents/工作/大掌柜/数据导出/环世退货登记表3.23.xlsx")
//                .sheet(0)
//                .head(Model.class)
//                .registerConverter(new ImageConverter())
//                .doReadSync();
//
//        int i = 0;


        excelImageExtract("/Users/linzhou/Documents/工作/大掌柜/数据导出/环世退货登记表3.23.xlsx", "XLSX", "/Users/linzhou/Documents/工作/大掌柜/数据导出/");

    }



    /**
     * @description: 提取execl文件中的图片
     * @author: Mr.Jkx
     * @time: 2023/1/11 17:31
     */
    public static void excelImageExtract(String filePath, String formart, String imageFilePath) {
        try {
            InputStream inp = new FileInputStream(filePath);
            if (formart.equals("XLS")) {
                HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(inp);
                List pictures = workbook.getAllPictures();
                HSSFSheet sheet = workbook.getSheetAt(0);
                for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                    if (shape instanceof HSSFPicture) {
                        HSSFPicture pic = (HSSFPicture) shape;
                        int pictureIndex = pic.getPictureIndex() - 1;
                        HSSFPictureData picData = (HSSFPictureData) pictures.get(pictureIndex);
                        // 保存图片到指定路径
                        byte[] data = picData.getData();
                        FileOutputStream out = new FileOutputStream(imageFilePath + "excel03_" + System.currentTimeMillis() + ".jpg");
                        out.write(data);
                        out.close();
                    }
                }
            } else if (formart.equals("XLSX")) {
                XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inp);
                XSSFSheet sheet = workbook.getSheetAt(0);
                int i= 0;
                for (POIXMLDocumentPart part : sheet.getRelations()) {
                    if (part instanceof XSSFDrawing) {
                        XSSFDrawing pic = (XSSFDrawing) part;
                        List<XSSFShape> shapes = pic.getShapes();
                        for (XSSFShape shape : shapes) {
                            XSSFPicture picture = (XSSFPicture) shape;
                            byte[] data = picture.getPictureData().getData();
                            FileOutputStream out = new FileOutputStream(imageFilePath + "excel07_" + (++i) + ".jpg");
                            out.write(data);
                            out.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
