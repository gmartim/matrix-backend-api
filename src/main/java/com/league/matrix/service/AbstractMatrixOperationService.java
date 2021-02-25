package com.league.matrix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.league.matrix.Constant;
import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.InternalErrorException;
import com.league.matrix.exception.NotSquareMatrixException;
import com.league.matrix.exception.OperationNotSupportedException;
import com.league.matrix.service.util.MatrixActService;
import com.league.matrix.service.util.MatrixCreateService;
import com.league.matrix.service.util.MatrixPrintService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMatrixOperationService {

	public static final String COMMA = ",";

	public static final String NEW_LINE = "\n";

	@Autowired
	protected MatrixActService matrixActService;

	@Autowired
	protected MatrixCreateService matrixCreateService;

	@Autowired
	protected MatrixPrintService matrixPrintService;

	public String run(MultipartFile multipartFile)
			throws NotSquareMatrixException, InternalErrorException, BadContentException {
		log.info("Started running the operation");

		List<List<Integer>> matrix;

		matrix = matrixCreateService.create(multipartFile);

		if (matrix == null) {
			log.error(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX);

			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX);
		}

		checkSquareMatrix(matrix);

		log.info("The matrix is going to be operated");

		return operate(matrix);
	}

	protected abstract String operate(List<List<Integer>> matrix)
			throws OperationNotSupportedException, BadContentException;

	/**
	 * Given a matrix check if its is square.
	 * 
	 * @param matrix the matrix to be checked
	 * 
	 * @throws NotSquareMatrixException if the matrix is not square
	 */
	protected void checkSquareMatrix(List<List<Integer>> matrix) throws NotSquareMatrixException {
		log.info("Started checking if matrix is square");

		int numberOfRows;

		numberOfRows = matrix.size();

		for (List<Integer> columns : matrix) {
			if (numberOfRows == columns.size()) {
				continue;
			}

			log.error(Constant.NOT_SQUARE_MATRIX_MESSAGE_NOT_SQUARE_MATRIX);

			throw new NotSquareMatrixException(Constant.NOT_SQUARE_MATRIX_MESSAGE_NOT_SQUARE_MATRIX);
		}
	}

}
