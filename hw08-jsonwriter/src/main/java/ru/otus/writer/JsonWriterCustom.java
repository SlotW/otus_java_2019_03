package ru.otus.writer;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class JsonWriterCustom {

    public static String objectToJsonString(Object object){
        if (object == null) return "null";
        Class clazzObject = object.getClass();
        if (Arrays.asList
                (
                        Byte.class, Short.class,
                        Integer.class, Long.class,
                        Float.class, Double.class,
                        Boolean.class
                ).contains(clazzObject)
        ) return object.toString();
        if(Character.class.equals(clazzObject) || String.class.equals(clazzObject)) return "\"" + object.toString() + "\"";
        if (clazzObject.isArray()){
            int sizeArr = Array.getLength(object);
            List<String> result = new ArrayList<>();
                for(int i = 0; i < sizeArr; i++){
                    result.add(objectToJsonString(Array.get(object, i)));
                }
                return "[" + result.stream().reduce((x,y)-> x + "," + y).orElse("") + "]";
        }
        if (Arrays.asList(clazzObject.getInterfaces()).contains(Collection.class)
                || Collections.singletonList(null).getClass().equals(clazzObject)
                || List.of().getClass().equals(clazzObject)){
            List<String> result = new ArrayList<>();
            for(Object objColl : (Collection)object){
                result.add(objectToJsonString(objColl));
            }
            return "[" + result.stream().reduce((x,y)-> x + "," + y).orElse("") + "]";
        }
        return objectToJsonObject(object).toString();
    }

    private static JsonObject objectToJsonObject(Object object) { // до вызова доходим, когда убеждаемся, что объект необходимо разобрать по полям
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        if (object != null) {

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();

                if (field.getType().isPrimitive()) { //если поле примитивного типа
                    jsonObjectBuilder.addAll(getJsonPrimitiveBuilder(field, object));

                } else if (field.getType().isArray()) { //если поле массив
                    jsonObjectBuilder.add(fieldName, getJsonArrayBuilder(field, object));

                } else { //не примитивы и не массивы
                    Object fieldObject = null;
                    try {
                        fieldObject = field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    if(fieldObject != null){
                        if(Arrays.asList(field.getType().getInterfaces()).contains(Collection.class)){ //если коллекция
                            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                            for (Object objColl : (Collection) fieldObject) {
                                jsonArrayBuilder.add(objectToJsonObject(objColl));
                            }
                            jsonObjectBuilder.add(fieldName, jsonArrayBuilder);
                        } else if("java.lang.String".equals(field.getType().getName())){ //если строка
                            jsonObjectBuilder.add(fieldName, fieldObject.toString());
                        } else { //всё остальное
                            jsonObjectBuilder.add(fieldName, objectToJsonObject(fieldObject));
                        }
                    }

                }
            }
        }
        return jsonObjectBuilder.build();
    }

    private static JsonObjectBuilder getJsonPrimitiveBuilder(Field field, Object obj){
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Object fieldObject = null;
        try {
            fieldObject = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fieldObject != null){
            Class clazzFieldObject = fieldObject.getClass();
            if(Integer.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (int) fieldObject);
            if (Short.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (short) fieldObject);
            if (Byte.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (byte) fieldObject);
            if (Long.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (long) fieldObject);
            if (Boolean.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (boolean) fieldObject);
            if (Character.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (char) fieldObject);
            if (Float.class.equals(clazzFieldObject))jsonObjectBuilder.add(field.getName(), (float) fieldObject);
            if (Double.class.equals(clazzFieldObject)) jsonObjectBuilder.add(field.getName(), (double) fieldObject);
        }
        return jsonObjectBuilder;
    }

    private static JsonArrayBuilder getJsonArrayBuilder(Field field, Object obj){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Object array = null;
        try {
            array = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(array != null){
            Class clazzType = field.getType().getComponentType();
            int sizeArr = Array.getLength(array);
            for(int i = 0; i < sizeArr; i++){
                if(clazzType.isPrimitive()) {
                    if(int.class.equals(clazzType)) jsonArrayBuilder.add(Array.getInt(array, i));
                    if(byte.class.equals(clazzType)) jsonArrayBuilder.add(Array.getByte(array, i));
                    if(short.class.equals(clazzType)) jsonArrayBuilder.add(Array.getShort(array, i));
                    if (long.class.equals(clazzType)) jsonArrayBuilder.add(Array.getLong(array, i));
                    if (boolean.class.equals(clazzType)) jsonArrayBuilder.add(Array.getBoolean(array, i));
                    if (double.class.equals(clazzType)) jsonArrayBuilder.add(Array.getDouble(array, i));
                    if (float.class.equals(clazzType)) jsonArrayBuilder.add(Array.getFloat(array, i));
                    if (char.class.equals(clazzType)) jsonArrayBuilder.add(Array.getChar(array, i));
                } else if (String.class.equals(clazzType)) {
                    jsonArrayBuilder.add((String) Array.get(array, i));
                } else {
                    jsonArrayBuilder.add(objectToJsonObject(Array.get(array, i)));
                }
            }
        }
        return jsonArrayBuilder;
    }

}
