package com.digiwin.ecims.core.util;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zaregoto on 2017/3/30.
 */
public class PropertyValidationUtil {
    private static Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

//    public static <T> ValidationResult validateEntity(T obj){
//        Set<ConstraintViolation<T>> set = validator.validate(obj);
//        if( CollectionUtils.isNotEmpty(set) ){
//            result.setHasErrors(true);
//            Map<String,String> errorMsg = new HashMap<String,String>();
//            for(ConstraintViolation<T> cv : set){
//                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
//            }
//            result.setErrorMsg(errorMsg);
//        }
//        return result;
//    }
}

