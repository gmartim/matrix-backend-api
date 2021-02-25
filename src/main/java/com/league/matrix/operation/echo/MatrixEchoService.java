package com.league.matrix.operation.echo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.OperationNotSupportedException;
import com.league.matrix.service.AbstractMatrixOperationService;
import com.league.matrix.service.MatrixOperationService;

import lombok.extern.slf4j.Slf4j;

@Service("matrixEchoService")
@Slf4j
public class MatrixEchoService extends AbstractMatrixOperationService implements MatrixOperationService {

	/**
	 * Operates the matrix joining the columns using a comma (",") and the rows
	 * using a new line ("\n").
	 * 
	 * @param matrix the matrix to be joined
	 * 
	 * @return the matrix's values joined by comma and new line
	 * 
	 * @throws BadContentException when matrix is null
	 */
	@Override
	protected String operate(List<List<Integer>> matrix) throws OperationNotSupportedException, BadContentException {
		log.info("Started operating echo");

		return matrixPrintService.join(matrix, COMMA, NEW_LINE);
	}

}
