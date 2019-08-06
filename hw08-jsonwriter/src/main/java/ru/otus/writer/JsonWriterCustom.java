package ru.otus.writer;

import javax.json.*;
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
            jsonObjectBuilder.add(field.getName(), convertToJsonValue(fieldObject));
        }
        return jsonObjectBuilder;
    }

    private static JsonValue convertToJsonValue(Object primitiveObject){
        Class clazzFieldObject = primitiveObject.getClass();
        JsonValue jsonValue = null;
        if(Integer.class.equals(clazzFieldObject) || Short.class.equals(clazzFieldObject) || Byte.class.equals(clazzFieldObject)) jsonValue = Json.createValue((int) primitiveObject);
        if (Long.class.equals(clazzFieldObject)) jsonValue = Json.createValue((long) primitiveObject);
        if (Character.class.equals(clazzFieldObject)) jsonValue = Json.createValue((String) primitiveObject);
        if (Float.class.equals(clazzFieldObject) || Double.class.equals(clazzFieldObject))jsonValue = Json.createValue((double) primitiveObject);
        if (Boolean.class.equals(clazzFieldObject)){
            if((boolean) primitiveObject) {
                jsonValue = JsonValue.TRUE;
            } else {
                jsonValue = JsonValue.FALSE;
            }
        }
        return jsonValue;
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
                    jsonArrayBuilder.add(convertToJsonValue(Array.get(array, i)));
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
