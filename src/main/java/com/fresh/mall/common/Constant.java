package com.fresh.mall.common;

import com.fresh.mall.exception.FreshMallException;
import com.fresh.mall.exception.FreshMallExceptionEnum;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Constant {
    public static final String SALT = "df532;.&^dslk[";
    public static final String FRESH_MALL_USER = "fresh_mall_user";

    public static String FILE_UPLOAD_DIR;
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc","price asc");

    }

    public interface SaleStatus{
        int NOT_SALE = 0;//product not for sale
        int SALE = 1;

    }

    public interface Cart{
        int UN_CHECKED = 0;
        int CHECKED = 1;//cart selected

    }

    public enum OrderStatusEnum{
        CANCELLED(0,"user already cancelled"),
        NOT_PAID(10,"NOT PAID"),
        PAID(20,"PAID"),
        SHIPPED(30,"shipped"),
        COMPLETED(40,"completed");
        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new FreshMallException(FreshMallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
