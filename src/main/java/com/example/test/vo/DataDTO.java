package com.example.test.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class DataDTO {

    @ExcelProperty(value = "id",index = 0)
    private String id;

    @ExcelProperty(value = "replenish_item_store_id",index = 1)
    private String replenishItemStoreId;

    @ExcelProperty(value = "tabNbr",index = 12)
    private String tabNbr;

    @ExcelProperty(value = "tabNbr1",index = 2)
    private String tabNbr1;
    @ExcelProperty(value = "tabNbr2",index = 3)
    private String tabNbr2;
    @ExcelProperty(value = "tabNbr3",index = 4)
    private String tabNbr3;
    @ExcelProperty(value = "tabNbr4",index = 5)
    private String tabNbr4;
    @ExcelProperty(value = "tabNbr5",index = 6)
    private String tabNbr5;
    @ExcelProperty(value = "tabNbr6",index = 7)
    private String tabNbr6;
    @ExcelProperty(value = "tabNbr7",index = 8)
    private String tabNbr7;
    @ExcelProperty(value = "tabNbr8",index = 9)
    private String tabNbr8;
    @ExcelProperty(value = "tabNbr9",index = 10)
    private String tabNbr9;
    @ExcelProperty(value = "tabNbr10",index = 11)
    private String tabNbr10;

}
