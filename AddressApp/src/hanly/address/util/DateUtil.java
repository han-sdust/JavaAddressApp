package hanly.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * �����������ݵĺ���
 */
public class DateUtil {

    /** ������ʾ���������ո�ʽ */
    private static final String DATE_PATTERN = "yyyy.MM.dd";

    /** ���ڸ�ʽ */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * ���غ��ʸ�ʽ�����ڣ�ʹ��
     * {@link DateUtil#DATE_PATTERN}
     *
     * @param date ���ڱ�ת��Ϊ�ַ���
     * @return ��ʽ���ַ���
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * ת���������ڸ�ʽ���ַ���{@link DateUtil#DATE_PATTERN}Ϊ{@link LocalDate}����
     *
     * ����ַ����޷�ת������null
     *
     * @param dateString �ַ�����ʽ����
     * @return ���ڶ�������޷�ת��ʱ��null
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * ����ַ����Ƿ�Ϊ�Ϸ�����
     *
     * @param dateString �ַ�����ʽ����
     * @return ���ںϷ�����true
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}