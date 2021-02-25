package com.league.matrix.service;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMatrixServiceTest {

	/**
	 * Given a content splits it by double space and single space, where double
	 * spaces generates row and single space generates column.
	 * 
	 * @param content
	 * 
	 * @return
	 */
	public List<List<Integer>> matrixGenerator(String content) {
		List<List<Integer>> matrix;

		matrix = new ArrayList<List<Integer>>();

		List<Integer> row;

		String rows[], columns[];

		rows = content.split("  ");

		for (String readingRow : rows) {
			columns = readingRow.split(" ");

			row = new ArrayList<>();

			for (String column : columns) {
				row.add(Integer.parseInt(column));
			}

			matrix.add(row);
		}

		return matrix;
	}

}
