package ru.otus.writer;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class JsonWriterCustom {

    public static JsonObject objectToJson(Object object) {
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
                                jsonArrayBuilder.add(objectToJson(objColl));
                            }
                            jsonObjectBuilder.add(fieldName, jsonArrayBuilder);
                        } else if("java.lang.String".equals(field.getType().getName())){ //если строка
                            jsonObjectBuilder.add(fieldName, fieldObject.toString());
                        } else { //всё остальное
                            jsonObjectBuilder.add(fieldName, objectToJson(fieldObject));
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
            String nameType = field.getType().getName();
            if("int".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (int) fieldObject);
            } else if ("short".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (short) fieldObject);
            } else if ("byte".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (byte) fieldObject);
            } else if ("long".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (long) fieldObject);
            } else if ("boolean".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (boolean) fieldObject);
            } else if ("char".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (char) fieldObject);
            } else if ("float".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (float) fieldObject);
            } else if ("double".equals(nameType)){
                jsonObjectBuilder.add(field.getName(), (double) fieldObject);
            }
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
            String nameType = field.getType().getComponentType().getName();
            int sizeArr = Array.getLength(array);
            for(int i = 0; i < sizeArr; i++){
                if("int".equals(nameType)){
                    jsonArrayBuilder.add(Array.getInt(array, i));
                } else if("byte".equals(nameType)){
                    jsonArrayBuilder.add(Array.getByte(array, i));
                } else if("short".equals(nameType)){
                    jsonArrayBuilder.add(Array.getShort(array, i));
                } else if ("long".equals(nameType)){
                    jsonArrayBuilder.add(Array.getLong(array, i));
                } else if ("boolean".equals(nameType)){
                    jsonArrayBuilder.add(Array.getBoolean(array, i));
                } else if ("double".equals(nameType)){
                    jsonArrayBuilder.add(Array.getDouble(array, i));
                } else if ("float".equals(nameType)){
                    jsonArrayBuilder.add(Array.getFloat(array, i));
                } else if ("char".equals(nameType)){
                    jsonArrayBuilder.add(Array.getChar(array, i));
                } else if("java.lang.String".equals(nameType)){
                    jsonArrayBuilder.add((String) Array.get(array, i));
                } else {
                    jsonArrayBuilder.add(objectToJson(Array.get(array, i)));
                }
            }
        }
        return jsonArrayBuilder;
    }

}
