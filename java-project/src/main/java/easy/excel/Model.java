package easy.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import easy.excel.converter.ImageConverter;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2023-03-28 13:39
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class Model {


    /**
     *
     */
    @ExcelProperty(value = "仓库名称")
    private String warehouseName;
    @ExcelProperty(value = "图片", converter = ImageConverter.class)
    private byte[] image;


    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
