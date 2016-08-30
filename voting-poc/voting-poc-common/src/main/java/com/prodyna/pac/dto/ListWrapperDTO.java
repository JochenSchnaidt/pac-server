package com.prodyna.pac.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class to support all lists of DTOs in the application. The class itself extends the {@link BaseDTO} so that it can provide meta data over an operation.
 *
 * @param <T>
 *            the class of list items
 */
public class ListWrapperDTO<T extends BaseDTO> extends BaseDTO {

	private List<T> list = new ArrayList<>();

	public ListWrapperDTO(List<T> list) {
		super();
		if (null != list) {
			this.list.addAll(list);
		}
	}

	public List<T> getList() {
		return list;
	}

}
