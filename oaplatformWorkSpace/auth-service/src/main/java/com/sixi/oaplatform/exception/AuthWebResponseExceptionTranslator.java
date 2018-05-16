package com.sixi.oaplatform.exception;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.sixi.oaplatform.config.constant.GenericErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * 鉴权异常返回处理器
 * @author wangwei
 * @date 2018/3/29
 */
@Slf4j
public class AuthWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception;
        HttpHeaders headers = new HttpHeaders();
        int httpStatus;

        if (e instanceof HystrixRuntimeException) {
            Throwable actual = ((HystrixRuntimeException) e).getFallbackException().getCause().getCause();

            oAuth2Exception = new OAuth2Exception(actual.getMessage());

            String code;
            String message;

            if (actual instanceof AbstractException) {
                httpStatus = ((AbstractException) actual).getStatus().value();
                code = ((AbstractException) actual).getCode().getCode()+"";
                message = ((AbstractException) actual).getCode().getMessage();
                log.error("AbstractException", actual);
            } else {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
                code = GenericErrorCodes.GENERIC_API_ERROR_CODE.getCode() + "";
                message = GenericErrorCodes.GENERIC_API_ERROR_CODE.getMessage();
                log.error("HystrixRuntimeException", actual);
            }

            oAuth2Exception.addAdditionalInformation("code", code);
            oAuth2Exception.addAdditionalInformation("message", message);
        } else {
            ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
            oAuth2Exception = responseEntity.getBody();
            httpStatus = HttpStatus.UNAUTHORIZED.value();

            String errorCode = oAuth2Exception.getMessage();

            oAuth2Exception.addAdditionalInformation("code", StringUtils.isNotEmpty(errorCode) ? errorCode : GenericErrorCodes.GENERIC_API_ERROR_CODE.getCode() + "");
            oAuth2Exception.addAdditionalInformation("message", "账号认证失败");
            log.error("OAuth2Exception", oAuth2Exception);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(oAuth2Exception, headers, HttpStatus.valueOf(httpStatus));
    }

}
