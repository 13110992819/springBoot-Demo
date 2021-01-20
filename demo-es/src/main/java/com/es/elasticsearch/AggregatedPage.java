package com.es.elasticsearch;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AggregatedPage<T> {
    /**
     * 以list返回的内容
     */
    List<T> content;

    /**
     *总数
     */
    long totalElements;
}
