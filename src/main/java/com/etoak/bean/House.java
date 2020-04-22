package com.etoak.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class House {

    private Integer id;

    @NotNull(message = "省份必填")
    private Integer province;

    @NotNull(message = "市必填")
    private Integer city;

    @NotNull(message = "所在区必填")
    private Integer area;
    //所在区县名称,可以从前端传入,也可以在后端根据area查询
    private String areaName;
    //租赁方式
    @NotNull(message = "租赁方式必填")
    @NotBlank(message = "租赁方式不能为空")
    private String rentMode;

    private String orientation;
    private String houseType;

    @NotNull(message = "租金必填")
    @Max(value = 100000,message = "租金不能超过10万")
    @Min(value = 100,message = "租金不能少于100")
    private Integer rental;

    @Size(min = 1,max = 10,message = "地址长度不能超过10")
    @NotBlank(message = "地址必填")
    private String address;
    private String pic;
    private String publishTime;

}
