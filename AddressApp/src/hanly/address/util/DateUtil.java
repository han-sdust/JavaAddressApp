package hanly.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 辅助处理数据的函数
 */
public class DateUtil {

    /** 用于提示和限制生日格式 */
    private static final String DATE_PATTERN = "yyyy.MM.dd";

    /** 日期格式 */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * 返回合适格式的日期，使用
     * {@link DateUtil#DATE_PATTERN}
     *
     * @param date 日期被转化为字符串
     * @return 格式化字符串
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * 转化定义日期格式的字符串{@link DateUtil#DATE_PATTERN}为{@link LocalDate}对象
     *
     * 如果字符串无法转化返回null
     *
     * @param dateString 字符串格式日期
     * @return 日期对象或者无法转化时的null
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * 检查字符串是否为合法日期
     *
     * @param dateString 字符串格式日期
     * @return 日期合法返回true
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}