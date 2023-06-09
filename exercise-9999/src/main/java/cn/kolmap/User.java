package cn.kolmap;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : kxhan
 * @create 2023/6/9 16:41
 */
@Data
@ColumnWidth(30)
public class User {

    @ExcelProperty("名称")
    @NotBlank(message = "名称不能为空")
    public String name;

    @ExcelProperty("年龄")
    private Integer age;
}
