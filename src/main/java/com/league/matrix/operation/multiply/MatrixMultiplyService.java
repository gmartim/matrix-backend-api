package com.league.matrix.operation.multiply;

import java.util.List;

import org.springframework.stereotype.Service;

import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.OperationNotSupportedException;
import com.league.matrix.service.AbstractMatrixOperationService;
import com.league.matrix.service.MatrixOperationService;

import lombok.extern.slf4j.Slf4j;

@Service("matrixMultiplyService")
@Slf4j
public class MatrixMultiplyService extends AbstractMatrixOperationService implements MatrixOperationService {

	/**
	 * Operates the matrix multiplying all columns and all the rows values and
	 * returning the result as a string.
	 * 
	 * @param matrix the matrix to be multiplied
	 * 
	 * @return the matrix's multiplication
	 * 
	 * @throws OperationNotSupportedException when the multiply reaches the maximum
	 *                                        value for an integer
	 * 
	 * @throws BadContentException            when matrix is null
	 */
	@Override
	protected String operate(List<List<Integer>> matrix) throws OperationNotSupportedException, BadContentException {
		log.info("Started operating multiply");

		int multiply;

		multiply = matrixActService.multiplyTotal(matrix);

		return Integer.toString(multiply);
	}

}
