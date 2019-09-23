package com.streambuf.subtitle.common.convert;

import java.time.ZoneOffset;

import com.streambuf.subtitle.common.modelmapper.jdk8.Jdk8Module;
import com.streambuf.subtitle.common.modelmapper.jsr310.Jsr310Module;
import com.streambuf.subtitle.common.modelmapper.jsr310.Jsr310ModuleConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class BeanConverter {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimePattern("yyyy-MM-dd HH:mm:ss") // default is yyyy-MM-dd HH:mm:ss
                .datePattern("yyyy-MM-dd") // default is yyyy-MM-dd
                .zoneId(ZoneOffset.UTC) // default is ZoneId.systemDefault()
                .build();
        modelMapper.registerModule(new Jsr310Module(config)).registerModule(new Jdk8Module());
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    /**
     * 获取 modelMapper
     *
     * @return
     */
    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 单个对象转换
     *
     * @param targetClass 目标对象
     * @param source      源对象
     * @return 转换后的目标对象
     */
    public static <T> T convert(Class<T> targetClass, Object source) {
        return getModelMapper().map(source, targetClass);
    }
}
