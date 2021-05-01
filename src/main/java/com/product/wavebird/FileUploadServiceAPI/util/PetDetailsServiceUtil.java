package com.product.wavebird.FileUploadServiceAPI.util;

import org.springframework.security.core.context.SecurityContextHolder;


public class PetDetailsServiceUtil {

    public static String getLoggedInUserId(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
