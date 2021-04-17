package com.fresh.mall.controller;

import com.fresh.mall.common.ApiRestResponse;
import com.fresh.mall.common.Constant;
import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.fresh.mall.model.request.AddProductReq;
import com.fresh.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * back-end product controller
 */
@RestController
public class ProductAdminController {
    @Autowired
    ProductService productService;
    @PostMapping("admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq){
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }
    //upload image api,parameter:need to save address(url,ip)
    //httpservletrequest, 2.file
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //generate filename UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString()+suffixName;
        //create folder
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //create file
        File destFile = new File(Constant.FILE_UPLOAD_DIR+newFileName);
        if(!fileDirectory.exists()){
            if(fileDirectory.mkdir()){
                throw new FreshMallException(FreshMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURI()+""))+"/images/"+
                    newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(FreshMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri){
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),
                    uri.getPort(),null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

}
