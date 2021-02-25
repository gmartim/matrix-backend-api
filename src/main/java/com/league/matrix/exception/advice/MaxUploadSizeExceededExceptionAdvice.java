package com.league.matrix.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.league.matrix.Constant;

@ControllerAdvice
public class MaxUploadSizeExceededExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException maxUploadSizeExceededException) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Constant.ADVICE_MESSAGE_MAX_UPLOAD_SIZE);
	}

}
