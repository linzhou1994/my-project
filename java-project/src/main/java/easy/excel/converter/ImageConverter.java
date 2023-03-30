package easy.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.Files;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-28 11:39
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class ImageConverter implements Converter<byte[]> {

    @Override
    public byte[] convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String imagePath = cellData.getStringValue();
        if (StringUtils.isBlank(imagePath)) {
            return null;
        }
        File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {

        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData convertToExcelData(byte[] imageBytes, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (imageBytes == null) {
            return new WriteCellData("");
        }
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        String imagePath = "image-" + UUID.randomUUID().toString() + ".png";
        Files.write(Paths.get(imagePath), outputStream.toByteArray());
        return new WriteCellData(imagePath);
    }

    @Override
    public Class<byte[]> supportJavaTypeKey() {
        return byte[].class;
    }
}