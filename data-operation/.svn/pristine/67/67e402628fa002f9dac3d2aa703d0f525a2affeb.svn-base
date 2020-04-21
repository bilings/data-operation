package com.hifo.dataoperation.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * 对象转换工具
 * @author jinzhichen
 */
@Slf4j
public class EntityUtils {

    /**
     * 复制属性值全员复制
     *
     * @param src  源
     * @param dist 目标
     */
    public static void copyProperties(Object src, Object dist) {
        copyProperties(src, dist, true);
    }

    /**
     * 复制属性值排除null值
     *
     * @param src  源
     * @param dist 目标
     */
    public static void copyPropertiesExceptNull(Object src, Object dist) {
        copyProperties(src, dist, false);
    }

    /**
     * 复制属性值
     *
     * @param src       源
     * @param dist      目标
     * @param allowNull 是否允许为null
     */
    public static void copyProperties(Object src, Object dist, boolean allowNull) {
        copyProperties(src, dist, allowNull, null);
    }

    /**
     * 复制属性值，根据指定属性复制
     *
     * @param src        源
     * @param dist       目标
     * @param properties 指定复制的数组属性
     */
    public static void copyProperties(Object src, Object dist, String[] properties) {
        copyProperties(src, dist, true, properties);
    }

    /**
     * 复制属性值
     *
     * @param src        源
     * @param dist       目标
     * @param allowNull  是否允许为null
     * @param properties 指定复制的数组属性
     */
    public static void copyProperties(Object src, Object dist, boolean allowNull, String[] properties) {
        if (Objects.isNull(src) || Objects.isNull(dist)) {
            return;
        }
        Class<?> distClz = dist.getClass();
        Stream.of(getAllFields(distClz)).forEach(distField -> {
            try {
                if ("serialVersionUID".equals(distField.getName())) {
                    return;
                }
                Class<?> srcClz = src.getClass();
                Optional<Field> optional = Stream.of(getAllFields(srcClz)).filter(f -> f.getName().equals(distField.getName())).findFirst();
                if (optional.isPresent()) {
                    optional.get().setAccessible(true);
                    Object v = optional.get().get(src);
                    if (!allowNull && Objects.isNull(v)) {
                        return;
                    }
                    if (ArrayUtils.getLength(properties) > 0 && !ArrayUtils.contains(properties, distField.getName())) {
                        return;
                    }
                    distField.setAccessible(true);
                    if (distField.getClass().getClassLoader() == null) {
                        distField.set(dist, optional.get().get(src));
                    } else {
                        copyProperties(optional.get().get(src), distField.get(dist), allowNull, null);
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("copy properties field error, srcClz : {}, distClz : {}, field : {}", src, dist, distField, e);
            }
        });
    }

    /**
     * 把对象转为map
     * key = 数据库对应字段名, value = 值
     *
     * @param model Model的继承类
     * @return Map
     */
    public static Map<String, Object> inverse(Model model) {
        if (Objects.isNull(model)) {
            return null;
        }
        Class clz = model.getClass();
        Field[] fields = getAllFields(clz);
        Map<String, Object> res = new HashMap<>(4);
        try {
            for (Field field : fields) {
                String key = null;
                if (field.isAnnotationPresent(TableId.class)) {
                    TableId annotation = field.getAnnotation(TableId.class);
                    key = annotation.value();
                } else if (field.isAnnotationPresent(TableField.class)) {
                    TableField annotation = field.getAnnotation(TableField.class);
                    key = annotation.value();

                }
                if (StringUtils.isNotBlank(key)) {
                    field.setAccessible(true);
                    if ("Boolean".equalsIgnoreCase(field.getType().getSimpleName())) {
                        if (field.get(model) != null && (Boolean) field.get(model)) {
                            res.put(key, "1");
                        } else {
                            res.put(key, "0");
                        }
                    } else {
                        res.put(key, field.get(model));
                    }
                }
            }
        } catch (IllegalAccessException i) {
            log.error("clazz : {}, inverse error", clz, i);
            return null;
        }
        return res;
    }

    /**
     * 将对象装换为map
     *
     * @param t 实体对象
     * @return map
     */
    public static <T> Map<String, Object> convert(T t) {
        Map<String, Object> map = new HashMap<>(16);
        if (t != null) {
            BeanMap beanMap = BeanMap.create(t);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将对象装换为map
     *
     * @param t 实体对象
     * @return map
     */
    public static <T> Map<String, Object> convertExecludeNullValue(T t) {
        Map<String, Object> map = new HashMap<>(16);
        if (t != null) {
            BeanMap beanMap = BeanMap.create(t);
            for (Object key : beanMap.keySet()) {
                Object value = beanMap.get(key);
                if (value != null) {
                    map.put(StringFormatUtils.underline(key.toString()), value);
                }
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map 值
     * @param clz 需转换的类
     * @return bean
     */
    public static <T> T convert(Map<String, Object> map, Class<T> clz) {
        T t = null;
        try {
            t = clz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("clazz : {}, newInstance error", clz, e);
        }
        BeanMap beanMap = BeanMap.create(t);
        beanMap.putAll(map);
        return t;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param list 列表
     * @return List<Map>
     */
    public static <T> List<Map<String, Object>> convert(List<T> list) {
        if (Objects.isNull(list)) {
            return null;
        }
        List<Map<String, Object>> listMap = new ArrayList<>(16);
        list.forEach(t -> listMap.add(convert(t)));
        return listMap;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param listMap 列表map
     * @param clazz   需转换的类
     * @return list<bean>
     */
    public static <T> List<T> convert(List<Map<String, Object>> listMap, Class<T> clazz) {
        if (Objects.isNull(listMap)) {
            return null;
        }
        List<T> list = new ArrayList<>(16);
        listMap.forEach(map -> list.add(convert(map, clazz)));
        return list;
    }

    /**
     * 获取所有field
     *
     * @param clz 类
     * @return Field[]
     */
    public static Field[] getAllFields(Class<?> clz) {
        Field[] fields = new Field[]{};
        if (Objects.nonNull(clz)) {
            do {
                fields = ArrayUtils.addAll(fields, clz.getDeclaredFields());
                clz = clz.getSuperclass();
            } while (clz != Object.class && clz != null);
        }
        return fields;
    }


    public static String objectToJSONString(Object obj) {
        return objectToJSONString(obj, "yyyy-MM-dd HH:mm:ss");
    }

    public static String objectToJSONString(Object obj, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            String jsonStr = mapper.writer().writeValueAsString(obj);
            return jsonStr;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    public static <T> T jsonStringToObject(String jsonStr) {
        return jsonStringToObject(jsonStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static <T> T jsonStringToObject(String jsonStr, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.reader().readValue(jsonStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T jsonStringToObject(String jsonStr, Class<T> clazz, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readerFor(clazz).readValue(jsonStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T jsonStringToObject(String jsonStr, Class<T> clazz) {
        return jsonStringToObject(jsonStr, clazz, "yyyy-MM-dd HH:mm:ss");
    }

    public static <T> T jsonStringToObject(String jsonStr, String dateFormat, Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            mapper.setDateFormat(new SimpleDateFormat(dateFormat));
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readerFor(javaType).readValue(jsonStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T jsonStringToObject(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        return jsonStringToObject(jsonStr, "yyyy-MM-dd HH:mm:ss", collectionClass, elementClasses);
    }

    /**
     * 将sourceValue转换成指定Java类型（类型兼容模式）
     *
     * @param sourceValue
     * @param targetValueJavaTypeName
     * @return
     */
    public static Object convertValueByTypeCompatiable(Object sourceValue, String targetValueJavaTypeName) {
        Object value = sourceValue;
        String type = StringUtils.trimToEmpty(targetValueJavaTypeName);
        if ("Float".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = Float.valueOf(((Number) value).floatValue());
            } else if (value instanceof String) {
                value = Float.parseFloat((String) value);
            }
        } else if ("Double".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = Double.valueOf(((Number) value).doubleValue());
            } else if (value instanceof String) {
                value = Double.parseDouble((String) value);
            }
        } else if ("Long".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = Long.valueOf(((Number) value).longValue());
            } else if (value instanceof String) {
                value = Long.parseLong((String) value);
            }
        } else if ("Short".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = Short.valueOf(((Number) value).shortValue());
            } else if (value instanceof String) {
                value = Short.parseShort((String) value);
            }
        } else if ("Byte".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = Byte.valueOf(((Number) value).byteValue());
            } else if (value instanceof String) {
                value = Byte.parseByte((String) value);
            }
        } else if ("Integer".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = Integer.valueOf(((Number) value).intValue());
            } else if (value instanceof String) {
                value = Integer.parseInt((String) value);
            }
        } else if ("BigInteger".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = BigInteger.valueOf(((Number) value).longValue());
            } else if (value instanceof String) {
                value = BigInteger.valueOf(Long.parseLong((String) value));
            }
        } else if ("BigDecimal".equalsIgnoreCase(type)) {
            if (value instanceof Number) {
                value = BigDecimal.valueOf(((Number) value).doubleValue());
            } else if (value instanceof String) {
                value = new BigDecimal((String) value);
            }
        } else if ("Date".equalsIgnoreCase(type)) {
            if (value instanceof LocalDate) {
                value = DateTimeUtils.localDateToDate((LocalDate) value);
            } else if (value instanceof LocalDateTime) {
                value = DateTimeUtils.localDateTimeToDate((LocalDateTime) value);
            } else if (value instanceof String) {
                value = DateTimeUtils.strToDate((String) value);
            }
        } else if ("LocalDate".equalsIgnoreCase(type)) {
            if (value instanceof Date) {
                value = DateTimeUtils.dateToLocalDate((Date) value);
            } else if (value instanceof LocalDateTime) {
                value = ((LocalDateTime) value).toLocalDate();
            } else if (value instanceof String) {
                value = DateTimeUtils.strToLocalDate((String) value);
            }
        } else if ("LocalDateTime".equalsIgnoreCase(type)) {
            if (value instanceof Date) {
                value = DateTimeUtils.dateToLocalDateTime((Date) value);
            } else if (value instanceof LocalDate) {
                value = DateTimeUtils.localDateToLocalDateTime((LocalDate) value);
            } else if (value instanceof String) {
                value = DateTimeUtils.strToLocalDateTime((String) value);
            }
        } else if ("LocalTime".equalsIgnoreCase(type)) {
            if (value instanceof Date) {
                value = DateTimeUtils.dateToLocalTime((Date) value);
            } else if (value instanceof LocalDateTime) {
                value = ((LocalDateTime) value).toLocalTime();
            } else if (value instanceof String) {
                value = DateTimeUtils.strToLocalTime((String) value);
            }
        }
        return value;
    }


}
