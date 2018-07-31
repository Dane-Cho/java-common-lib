package com.prompt.common.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Multipart Validator
 */
public class MultipartValidator implements ConstraintValidator<Multipart, MultipartFile> {

    private String extention;

    @Override
    public void initialize(Multipart constraintAnnotation) {
        this.extention = constraintAnnotation.extention();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        String fileName = multipartFile.getOriginalFilename();

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        if (!extention.equals(fileExtension)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("multipart [" + extention + "] extention invalid").addBeanNode().addConstraintViolation();
            return false;
        }

        return true;
    }

}
