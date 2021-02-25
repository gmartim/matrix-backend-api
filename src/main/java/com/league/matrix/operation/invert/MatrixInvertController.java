package com.league.matrix.operation.invert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.league.matrix.service.MatrixOperationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/invert")
@Slf4j
public class MatrixInvertController {

	@Autowired
	@Qualifier("matrixInvertService")
	private MatrixOperationService matrixInvertService;

	@PostMapping
	public ResponseEntity<?> post(@RequestParam(name = "file", required = true) MultipartFile multipartFile) {
		if (log.isInfoEnabled()) {
			log.info("Started handling the path: [/invert], the method: [post], multipartFile.originalFilename: [{}]",
					multipartFile.getOriginalFilename());
		}

		try {
			String content;

			content = matrixInvertService.run(multipartFile);

			return ResponseEntity.ok(content);
		} catch (BadContentException exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		} catch (InternalErrorException exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
	}

}
