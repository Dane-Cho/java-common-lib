package com.prompt.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * 커스텀 RestTemplate 리스폰스 에러 핸들러
 *
 * RestTemplate 구현체를 생성 시 기본적으로 DefaultResponseErrorHandler를 사용한다.
 * DefaultResponseErrorHandler는 기본적으로 400번대와 500번대 statusCode에 대해서 에러 헨들링을 강제하고 있다.
 * 500번대 statusCode를 제외한 나머지 statusCode는 로직레벨에서 처리를 원할 경우 해당 구현체로 교체한다.
 */
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected boolean hasError(HttpStatus statusCode) {
        return (statusCode.series() == HttpStatus.Series.SERVER_ERROR);
    }

}
