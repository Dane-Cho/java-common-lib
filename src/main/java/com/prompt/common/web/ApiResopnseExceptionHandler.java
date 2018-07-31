package com.prompt.common.web;

import com.prompt.common.model.ApiResponseModel;
import com.prompt.common.model.BasedException;
import com.prompt.common.model.ExceptionTypeable;
import com.prompt.common.model.ParamFieldValidModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * API 응답 익셉션 핸들러 추상 클래스
 */
public abstract class ApiResopnseExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiResopnseExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody public ApiResponseModel paramFieldValidExceptionHandler(BindException ex) throws Exception {

        log.debug("ParamFieldValidException Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        setInvalidGlobal(apiResponse, ex.getGlobalError());
        setInvalidFieldList(apiResponse, ex.getFieldErrors());

        return apiResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody public ApiResponseModel methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) throws Exception {

        log.debug("MethodArgumentNotValidException Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        setInvalidGlobal(apiResponse, ex.getBindingResult().getGlobalError());
        setInvalidFieldList(apiResponse, ex.getBindingResult().getFieldErrors());

        return apiResponse;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody public ApiResponseModel methodArgumentNotValidExceptionHandler(MethodArgumentTypeMismatchException ex) throws Exception {

        log.debug("MethodArgumentTypeMismatchException Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        String message = "Failed to convert value of required type [" + ClassUtils.getShortName(ex.getRequiredType()) + "]";

        apiResponse.putError("invalidMessages", Collections.singletonList(new ParamFieldValidModel(ex.getName(), ex.getErrorCode(), message)));

        return apiResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody public ApiResponseModel constraintViolationExceptionHandler(ConstraintViolationException ex) throws Exception {

        Set<String> messages = null;

        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();

        if (constraintViolationSet != null && !constraintViolationSet.isEmpty()) {
            messages = new HashSet<>(constraintViolationSet.size());
            messages.addAll(constraintViolationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        }

        log.debug("ConstraintViolationException Handling : {}", messages);

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        apiResponse.putError("invalidMessages", messages);

        return apiResponse;
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            HttpMediaTypeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody public ApiResponseModel badRequestExceptionHandler(Exception ex) throws Exception {

        log.debug(ex.getClass().getSimpleName() + " Handling : {}", ex.getMessage());

        ApiResponseModel apiResponse = createBadRequestApiResponse();

        apiResponse.putError(ex.getMessage());

        return apiResponse;
    }

    @ExceptionHandler(BasedException.class)
    @ResponseBody public ResponseEntity<ApiResponseModel> basedExceptionHandler(BasedException ex) throws Exception {

        log.debug("BasedException Handling : {}", ex.getMessage());

        ExceptionTypeable exceptionType = ex.getExceptionType();

        ApiResponseModel apiResponse = new ApiResponseModel(exceptionType.getResultCode(), exceptionType.getResultMessage());

        Object exceptionData = ex.getExceptionData();

        if (exceptionData != null) {
            apiResponse.putError(exceptionData);
        }

        return new ResponseEntity<>(apiResponse, exceptionType.getStatusCode());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody public ApiResponseModel errorExceptionHandler(Throwable ex) throws Exception {

        log.error("Exception Handling...", ex);

        ApiResponseModel apiResponse = new ApiResponseModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );

        return apiResponse;
    }

    private void setInvalidGlobal(ApiResponseModel apiResponse, ObjectError objectError) throws Exception {

        if (apiResponse != null && objectError != null) {

            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("validCode", objectError.getCode());
            resultMap.put("message", objectError.getDefaultMessage());

            apiResponse.putError("invalidGlobal", resultMap);
        }
    }

    private void setInvalidFieldList(ApiResponseModel apiResponse, List<FieldError> fieldErrorList) throws Exception {

        if (apiResponse != null && fieldErrorList != null && !fieldErrorList.isEmpty()) {

            List<ParamFieldValidModel> invalidFiledList = new ArrayList<>();

            fieldErrorList.forEach(fieldError -> invalidFiledList.add(
                    new ParamFieldValidModel(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage())
            ));

            apiResponse.putError("invalidMessages", invalidFiledList);
        }
    }

    private ApiResponseModel createBadRequestApiResponse() throws Exception {
        return new ApiResponseModel(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}
