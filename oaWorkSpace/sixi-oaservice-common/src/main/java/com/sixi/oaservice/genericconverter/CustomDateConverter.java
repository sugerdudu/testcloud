/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sixi.oaservice.genericconverter;

import com.sixi.oaplatform.common.kits.DateKit;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 日期转换器(支持时间戳和日期pattern)
 *
 * @author 喵♂呜
 */
@Component
public class CustomDateConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<>();
        pairs.add(new ConvertiblePair(String.class, Date.class));
        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        if (sourceType.getType() == String.class) {
            DateTimeFormat ann = targetType.getAnnotation(DateTimeFormat.class);
            // 使用默认的解析类(该解析支持时间戳和字符串)
            if (ann == null) {
                return DateKit.parseNothrows((String) source);
            }
            // 根据注解返回的值解析日期
            // TODO 暂时不考虑缓存
            // Format 最慢情况下 每秒也可以达到 20w 次 如果需要 可以用 ThreadLocal 缓存
            SimpleDateFormat sdf = new SimpleDateFormat(ann.pattern());
            try {
                return sdf.parse((String) source);
            } catch (ParseException e) {
                throw new IllegalArgumentException(String.format("日期 %s 和指定格式 %s 不匹配!", source, ann.pattern()), e);
            }
        }
        return source;
    }
}
