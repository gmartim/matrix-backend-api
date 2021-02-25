package com.league.matrix.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.league.matrix.Constant;
import com.league.matrix.exception.BadContentException;
import com.league.matrix.exception.OperationNotSupportedException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatrixActService {

	/**
	 * Transposes a matrix moving each row to be a new column.
	 * 
	 * @param matrix the matrix to transpose
	 * 
	 * @return the transpose matrix
	 * 
	 * @throws BadContentException when matrix is null
	 */
	public List<List<Integer>> transpose(List<List<Integer>> matrix) throws BadContentException {
		log.info("Started transpose the matrix");

		if (matrix == null) {
			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX);
		}

		int columnIndex;

		Map<Integer, List<Integer>> columnsTransformed;

		columnsTransformed = new HashMap<Integer, List<Integer>>();

		List<Integer> rowTransformed;

		for (List<Integer> row : matrix) {
			columnIndex = 0;

			for (Integer column : row) {
				rowTransformed = columnsTransformed.get(columnIndex);

				if (rowTransformed == null) {
					rowTransformed = new ArrayList<Integer>();

					columnsTransformed.put(columnIndex, rowTransformed);
				}

				rowTransformed.add(column);

				columnIndex++;
			}
		}

		List<List<Integer>> transformedMatrix;

		transformedMatrix = new ArrayList<List<Integer>>();

		for (Integer rowIndex : columnsTransformed.keySet()) {
			transformedMatrix.add(columnsTransformed.get(rowIndex));
		}

		log.info("Transformed the matrix");

		return transformedMatrix;
	}

	/**
	 * Sums all columns from all rows in a matrix.
	 * 
	 * @param matrix the matrix to be summed
	 * 
	 * @return the sum of all values
	 * 
	 * @throws OperationNotSupportedException when the sum reaches the maximum value
	 *                                        for an integer
	 * 
	 * @throws BadContentException            when matrix is null
	 */
	public int sumTotal(List<List<Integer>> matrix) throws OperationNotSupportedException, BadContentException {
		log.info("Started summing the matrix");

		if (matrix == null) {
			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX);
		}

		int sum;

		sum = 0;

		for (List<Integer> row : matrix) {
			for (Integer column : row) {
				if (column >= (Integer.MAX_VALUE - sum)) {
					throw new OperationNotSupportedException(
							Constant.OPERATION_NOT_SUPPORTED_MESSAGE_OPERATION_NOT_SUPPORTED);
				}

				sum += column;
			}
		}

		log.info("Summed the matrix");

		return sum;
	}

	/**
	 * Multiples all columns from all rows in a matrix.
	 * 
	 * @param matrix the matrix to be multiplied
	 * 
	 * @return the multiply of all values
	 * 
	 * @throws OperationNotSupportedException when the multiply reaches the maximum
	 *                                        value for an integer
	 * 
	 * @throws BadContentException            when matrix is null
	 */
	public int multiplyTotal(List<List<Integer>> matrix) throws OperationNotSupportedException, BadContentException {
		log.info("Started multipling the matrix");

		if (matrix == null) {
			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX);
		}

		int multiply;

		multiply = 0;

		long currentMultiplying;

		boolean firstColumn;

		firstColumn = true;

		for (List<Integer> row : matrix) {
			for (Integer column : row) {
				if (firstColumn) {
					currentMultiplying = column;

					firstColumn = false;
				} else {
					currentMultiplying = (long) multiply * (long) column;
				}

				if (currentMultiplying >= (Integer.MAX_VALUE - multiply)) {
					throw new OperationNotSupportedException(
							Constant.OPERATION_NOT_SUPPORTED_MESSAGE_OPERATION_NOT_SUPPORTED);
				}

				multiply = (int) currentMultiplying;
			}
		}

		log.info("Multiplied the matrix");

		return multiply;
	}

}
