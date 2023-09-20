package com.chris.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class DataUtils {

    private DataUtils() {

    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean notNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean nullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean nullOrEmpty(Collection objects) {
        return objects == null || objects.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().equals("");
    }

    public static boolean isNullOrEmpty(final Object obj) {
        return obj == null || obj.toString().isEmpty();
    }

    public static boolean notNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean notNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static String parseToString(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static String parseToString(Object obj, String str) {
        return obj != null ? String.valueOf(obj) : str;
    }

    public static Integer parseToInt(Object obj, Integer value) {
        String tmp = parseToString(obj);
        if (isNull(tmp)) {
            return null;
        }
        return Integer.valueOf(tmp);
    }

    public static Integer parseToInt(String value) {
        return parseToInt(value, null);
    }

    public static Integer parseToInt(Object value) {
        String tmp = parseToString(value);
        if (isNull(tmp)) {
            return null;
        }
        return Integer.valueOf(tmp);
    }

    public static boolean nullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }

    public static Character parseToCharacter(Object obj) {
        return (Character) obj;
    }

    public static Double parseToDouble(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return Double.parseDouble(parseToString(obj));
    }

    public static BigInteger parseToBigInteger(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return new BigInteger(parseToString(obj));
    }

    public static Long parseToLong(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return Long.parseLong(parseToString(obj));
    }

    public static Short parseToShort(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return Short.parseShort(parseToString(obj));
    }

    public static Integer parseToInteger(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return Integer.parseInt(parseToString(obj));
    }

    public static LocalDate parseToLocalDate(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return LocalDate.parse(parseToString(obj));
    }

    public static LocalDateTime parseToLocalDateTime(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return LocalDateTime.parse(parseToString(obj));
    }

    public static LocalDate longToLocalDate(Long input) {
        if (isNull(input) || input <= 0L) {
            return null;
        }
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(input), ZoneId.systemDefault());
        return date.toLocalDate();
    }

    public static LocalDateTime parseToLocalDatetime(Object value) {
        if (value == null)
            return null;
        String tmp = parseToString(value, null);
        if (tmp == null)
            return null;

        try {
            LocalDateTime rtn = convertStringToLocalDateTime(tmp, "yyyy-MM-dd HH:mm:ss");
            return rtn;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static LocalDateTime longToLocalDateTime(Long input) {
        if (isNull(input) || input <= 0L) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(input), ZoneId.systemDefault());
    }

    public static Long localDateToLong(LocalDate input) {
        return input.toEpochDay();
    }

    public static Long localDateTimeToLong(LocalDateTime input) {
        ZonedDateTime zdt = input.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public static String objectToJson(Object data, String defaultValue) {
        if (isNull(data)) {
            return defaultValue;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return "";
        }
    }

    public static String objectToJson(Object data) {
        return objectToJson(data, "");
    }

//    private static <T> T jsonToObjectFronGson(String jsonData, Class<T> classOutput) {
//        try {
//            Gson gson = new Gson();
//            return gson.fromJson(jsonData, classOutput);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//        }
//        return null;
//    }

//    public static <T> T jsonToObject(String jsonData, Class<T> classOutput) {
//        try {
//            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
//                    false);
//            return mapper.readValue(jsonData, classOutput);
//        } catch (Exception ex) {
//            return jsonToObjectFronGson(jsonData, classOutput);
//        }
//    }

    public static LocalDateTime convertStringToLocalDateTime(String value, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        if (value == null) {
            return null;
        } else if (value.contains(".")) {
            value = value.substring(0, value.indexOf('.'));
        }
        return LocalDateTime.parse(value, formatter);
    }

    public static String localDateToString(LocalDate value, String format) {
        if (!notNull(value)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return value.format(formatter); // "1986-04-08 12:30"
    }

    public static String localDateTimeToString(LocalDateTime value, String format) {
        if (!notNull(value)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return value.format(formatter); // "1986-04-08 12:30"
    }

    public static String formatIsdn(String msisdn) {
        if (msisdn.startsWith("0")) {
            return msisdn.substring(1);
        } else if (msisdn.startsWith("84") && msisdn.length() == 11) {
            return msisdn.substring(2);
        } else if (msisdn.startsWith("+84")) {
            return msisdn.substring(3);
        }
        return msisdn;
    }

    public static String formatMsisdn(String isdn) {
        if (isdn.startsWith("84") && isdn.length() >= 11) {
            return isdn;
        } else if (isdn.startsWith("+84")) {
            return isdn.substring(1);
        } else if (isdn.startsWith("0")) {
            isdn = isdn.substring(1);
        }
        return String.format("84%s", isdn);
    }

    public static boolean isInteger(Object obj) {
        return obj == parseToInteger(obj);
    }

    public static String randomNumberByDate() {
        String randomNumber = String.valueOf(System.nanoTime());
        if (randomNumber.startsWith("0")) {
            randomNumber = randomNumber.replaceFirst("0", "9");
        }
        return randomNumber;
    }

    public static String camelToSnake(String str) {
        // Regular Expression
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";

        // Replace the given regex
        // with replacement string
        // and convert it to lower case.
        str = str
                .replaceAll(
                        regex, replacement)
                .toLowerCase();

        // return string
        return str;
    }

    public static String replaceAll(String input, String find, String replace) {
        if (DataUtils.notNull(input)) {
            return input.replaceAll(find, replace);
        }
        return null;
    }

    // import file mau
    public static InputStream readInputStreamResource(String path) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource(path);
        return classPathResource.getInputStream();
    }

    public static byte[] readFileResource(String path) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource(path);
        return classPathResource.getInputStream().readAllBytes();
    }
    // end

    public static boolean safeEqual(Object obj1, Object obj2) {
        return ((obj1 != null) && (obj2 != null) && obj2.toString().equals(obj1.toString()));
    }

    public static boolean safeEqualIgnoreCase(Object obj1, Object obj2) {
        return ((obj1 != null) && (obj2 != null) && obj2.toString().equalsIgnoreCase(obj1.toString()));
    }

    public static String htmlUnescape(String data) {
        if (data == null) {
            return null;
        }
        return HtmlUtils.htmlUnescape(data);
    }

    public static String formatCurrency(BigInteger number) {
        Locale loc = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(loc);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        return nf.format(number).trim();
    }

    public static <T> List<T> jsonToList(String json, Class<T> classOutput) throws IOException {
        if (isNull(json)) {
            return Collections.emptyList();
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper
                .readValue(json, typeFactory.constructCollectionType(List.class, classOutput));
    }

    public static <T> List<String> getNameOfProperty(Class<T> classOutput) throws Exception {
        List<String> classNameList = new ArrayList<>();
        Field[] fields = classOutput.getDeclaredFields();
        for (Field field : fields) {
            classNameList.add(field.getName());
        }
        return classNameList;
    }

}
