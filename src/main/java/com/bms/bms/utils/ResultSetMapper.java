package com.bms.bms.utils;

import com.bms.bms.annotations.JoinMappingId;
import com.bms.bms.annotations.RelationField;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ResultSetMapper {

    private static final Map<Class<?>, Function<Object, ?>> typeConverters = new HashMap<>();

    public static String toSnakeCase(String camelCase) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return camelCase.replaceAll(regex, replacement).toLowerCase();
    }

    static {
        typeConverters.put(long.class, value -> ((Number) value).longValue());
        typeConverters.put(Long.class, value -> ((Number) value).longValue());
        typeConverters.put(int.class, value -> ((Number) value).intValue());
        typeConverters.put(Integer.class, value -> ((Number) value).intValue());
        typeConverters.put(double.class, value -> ((Number) value).doubleValue());
        typeConverters.put(Double.class, value -> ((Number) value).doubleValue());
        typeConverters.put(float.class, value -> ((Number) value).floatValue());
        typeConverters.put(Float.class, value -> ((Number) value).floatValue());
        typeConverters.put(boolean.class, value -> ((Boolean) value));
        typeConverters.put(Boolean.class, value -> ((Boolean) value));
        typeConverters.put(String.class, Object::toString);
        typeConverters.put(java.sql.Date.class, value -> value instanceof Timestamp ? new java.sql.Date(((Timestamp) value).getTime()) : value);
        typeConverters.put(Timestamp.class, value -> value instanceof Timestamp ? (Timestamp) value : new Timestamp(((java.sql.Date) value).getTime()));
        typeConverters.put(BigDecimal.class, value -> new BigDecimal(value.toString()));
        typeConverters.put(BigInteger.class, value -> ((BigInteger) value).longValue());
    }
    public static <T> T mapResultSetToObject(ResultSet rs, Class<T> clazz) throws SQLException {
        return mapResultSetToObject(rs, clazz, false);
    }
    public static <T> T mapResultSetToObject(ResultSet rs, Class<T> clazz, Boolean mapFieldName) throws SQLException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                System.out.println("Field: " + field.getName());
                if (field.isAnnotationPresent(RelationField.class)) {
                    System.out.println("Skipping field with @RelationField annotation");
                    continue; // Skip fields with @RelationField annotation
                }
                field.setAccessible(true);
                String columnName = toSnakeCase(field.getName());
                System.out.println("Column Name: " + columnName);
                if(mapFieldName && field.isAnnotationPresent(JoinMappingId.class)){
                    columnName = field.getAnnotation(JoinMappingId.class).value();
                    System.out.println("COLUNAME: "+columnName);
                }
                Object value = rs.getObject(columnName);

                if (value != null) {
                    Function<Object, ?> converter = typeConverters.get(field.getType());
                    if (converter != null) {
//                        System.out.println("Converter found for field: " + field.getName()+" "+field.getType());
                        field.set(instance, converter.apply(value));
                    } else if (field.getType().isEnum()) {
                        @SuppressWarnings("unchecked")
                        Class<? extends Enum> enumType = (Class<? extends Enum>) field.getType();
                        field.set(instance, Enum.valueOf(enumType, value.toString()));
                    } else {
                        field.set(instance, value);
                    }
                }
            }
            System.out.println("NOTE: Mapping ResultSet to object successful" + instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to object", e);
        }
    }
}