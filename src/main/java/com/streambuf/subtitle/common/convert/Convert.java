package com.streambuf.subtitle.common.convert;

import java.io.Serializable;

public class Convert implements Serializable {


    public <T> T convert(Class<T> clazz) {
        return BeanConverter.convert(clazz, this);
    }
}
