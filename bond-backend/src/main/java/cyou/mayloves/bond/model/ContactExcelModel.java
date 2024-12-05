package cyou.mayloves.bond.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

@Data
public class ContactExcelModel {

    @ExcelProperty("编号")
    @ColumnWidth(10)
    private Integer no;

    @ExcelProperty("联系人名称")
    @ColumnWidth(20)
    private String name;

    @ExcelProperty("标签")
    @ColumnWidth(15)
    private String tag;

    @ExcelProperty("性别")
    @ColumnWidth(10)
    private String sex;

    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty("出生日期")
    @ColumnWidth(15)
    private Date birthday;

    @ExcelProperty("联系电话")
    @ColumnWidth(15)
    private String phone;

    @ExcelProperty("邮箱地址")
    @ColumnWidth(25)
    private String email;

    @ExcelProperty("通信地址")
    @ColumnWidth(30)
    private String address;

    @ExcelProperty("描述信息")
    @ColumnWidth(50)
    private String description;

}
