package com.prodyna.pac.dto;

import java.util.ArrayList;
import java.util.List;

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
