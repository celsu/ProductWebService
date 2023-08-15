package com.api.product.repository;

import java.util.List;

public interface Database {

    List<String> select(Long id);

    Long insert(String row);

    boolean update(Long id, String newRow);

    boolean delete(Long id);

}
