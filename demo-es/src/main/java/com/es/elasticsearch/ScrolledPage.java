package com.es.elasticsearch;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScrolledPage<T> extends AggregatedPage<T> {
    String scrollId;
    boolean hasContent;
}
