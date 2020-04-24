package com.etoak.controller;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;
import com.etoak.exception.ParamException;
import com.etoak.service.HouseService;
import com.etoak.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validation;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/house")
@Slf4j
public class HouseController {

    @Value("${upload.dir}")
    private String uploadDirectory;

    @Value("${upload.savePathPrefix}")
    private String savePathPrefix;

    @Autowired
    HouseService houseService;

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "house/add";
    }

    /*第二种方式*/
    @PostMapping("/add")
    public String add(@RequestParam("file")MultipartFile file, House house) throws IOException,IllegalStateException {
        //校验参数
        ValidationUtil.validate(house);


        //上传文件
        String originalFilename = file.getOriginalFilename();
        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        //  新文件名称,原始文件名_uuid.文件后缀
        String newFilename = prefix + "_" + originalFilename;

        File destFile = new File(this.uploadDirectory,newFilename);
        file.transferTo(destFile);

        house.setPic(this.savePathPrefix + newFilename);
        houseService.addHouse(house);
        return "redirect:/house/toAdd";

    }

    /*第一种方式校验*/
    @PostMapping("/add2")
    public String add2(@RequestParam("file")MultipartFile file, @Valid House house,
                      BindingResult bingResult) throws IOException,IllegalStateException {

        List<ObjectError> allErrors = bingResult.getAllErrors();
        if(CollectionUtils.isNotEmpty(allErrors)){
            StringBuffer msgBuffer = new StringBuffer();
            for(ObjectError objectError : allErrors){
                String message = objectError.getDefaultMessage();
                msgBuffer.append(message).append(";");
            }

            throw new ParamException("参数校验失败: " + msgBuffer.toString());
        }


        //上传文件
        String originalFilename = file.getOriginalFilename();
        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        //  新文件名称,原始文件名_uuid.文件后缀
        String newFilename = prefix + "_" + originalFilename;

        File destFile = new File(this.uploadDirectory,newFilename);
        file.transferTo(destFile);

        house.setPic(this.savePathPrefix + newFilename);
        houseService.addHouse(house);
        return "redirect:/house/toAdd";

    }

    //房源列表查询
    @GetMapping(value = "/list",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Page<HouseVo> queryList(@RequestParam(required = false,defaultValue = "1") int pageNum,
                                 @RequestParam(required = false,defaultValue = "10") int pageSize,
                                 HouseVo houseVo,
                                 @RequestParam(value = "rentalList[]", required = false) String[] rentalList) {
        log.info("pageNum - {},pageSize - {},houseVo - {},rentalList- {}",pageNum,pageSize,houseVo,rentalList);
        return houseService.queryList(pageNum,pageSize,houseVo,rentalList);

    }
    //跳转到列表页面
    @GetMapping("/toList")
    public String toList(){
        return "house/list";
    }


    @PutMapping("/update")
    public String update(House house){
        log.info("house - {}",house);
        houseService.updateHouse(house);
        return  "redirect:/house/toList";
    }




}
