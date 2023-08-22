
package datastructuresproject3;

/**
 * @file GenericCast.java
 * @description A program that get recommendations according to the target user and movies.
 * @assignment Data Structures Project 3: Movie Recommendation
 * @date 26.05.2023
 * @authors Beyza Yıldızlı @beyzayildizli10@gmail.com & Merve Öğ @merve.og@stu.fsm.edu.tr
 */
public class GenericCast {
    /*Converts the given generic value to an integer.*/
    public static <T> int convertGenericToInt(T value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
            }
        }
        throw new IllegalArgumentException("Converting process couldn't be done!");
    }
    
    /*Converts the given generic value to String.*/
    public <T> String convertGenericToString(T genericValue) {
        return String.valueOf(genericValue);
    }

    /*Makes the given String value generic in the desired class.*/
    public static <T extends Number> T convertToType(String value, Class<T> tClass) {
        if (tClass == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (tClass == Double.class) {
            return (T) Double.valueOf(value);
        } else if (tClass == Float.class) {
            return (T) Float.valueOf(value);
        } else if (tClass == Long.class) {
            return (T) Long.valueOf(value);
        } else if (tClass == Short.class) {
            return (T) Short.valueOf(value);
        } else if (tClass == Byte.class) {
            return (T) Byte.valueOf(value);
        } else {
            throw new IllegalArgumentException("Unsupported class type: " + tClass);
        }
    }

}
