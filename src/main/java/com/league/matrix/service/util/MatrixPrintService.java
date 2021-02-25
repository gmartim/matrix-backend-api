package com.league.matrix.service.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.league.matrix.Constant;
import com.league.matrix.exception.BadContentException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatrixPrintService {

	/**
	 * Joins a matrix adding the column separator between columns and row separator
	 * between rows.
	 * 
	 * @param matrix          the matrix to be joined
	 * 
	 * @param columnSeparator the separator to be used to join the columns, it uses
	 *                        an empty string if null
	 * 
	 * @param rowSeparator    the separator to be used to join the rows, it uses an
	 *                        empty string if null
	 * 
	 * @return the matrix joined
	 * 
	 * @throws BadContentException when matrix is null
	 */
	public String join(List<List<Integer>> matrix, String columnSeparator, String rowSeparator)
			throws BadContentException {
		log.info("Started joing the matrix");

		if (matrix == null) {
			throw new BadContentException(Constant.BAD_CONTENT_MESSAGE_NULL_MATRIX);
		}

		if (matrix.isEmpty()) {
			return StringUtils.EMPTY;
		}

		if (columnSeparator == null) {
			columnSeparator = StringUtils.EMPTY;
		}

		if (rowSeparator == null) {
			rowSeparator = StringUtils.EMPTY;
		}

		List<String> joinedRows;

		joinedRows = new ArrayList<>();

		String joinedColumns;

		for (List<Integer> columns : matrix) {
			joinedColumns = StringUtils.join(columns, columnSeparator);

			joinedRows.add(joinedColumns);
		}

		String joined;

		joined = StringUtils.join(joinedRows, rowSeparator);

		log.info("Joined the matrix");

		return joined;
	}

}
