package com.example.demo.model.utilities;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.model.DTO.CustomerDto;
import com.example.demo.model.DTO.EmployeeDTO;
import com.example.demo.model.info.PaginationInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommonUtil {

    private static String localIp = null;
    private static ModelMapper mapper = null;
    public static final String F_DDMMYYYY = "dd-MM-yyyy";
    public static final String FORMAT_DDMMYYYY = "dd/MM/yyyy";
    public static final String F_YYYYMMDD = "yyyy-MM-dd";

    public static final String F_DDMMYYYY_HHMM = "dd-MM-yyyy HH:mm";

    private CommonUtil() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String getLocalIp() {
        if (!isNullOrEmpty(localIp)) {
            return localIp;
        }
        try {
            int lowest = Integer.MAX_VALUE;
            InetAddress result = null;
            for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp() && (ifc.getIndex() < lowest || result == null)) {
                    lowest = ifc.getIndex();
                    result = getFirstNonLoopbackIPv4Address(ifc);
                }
            }
            if (result != null) {
                return result.getHostAddress();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static InetAddress getFirstNonLoopbackIPv4Address(NetworkInterface ifc) {
        for (Enumeration<InetAddress> addrs = ifc.getInetAddresses(); addrs.hasMoreElements(); ) {
            InetAddress address = addrs.nextElement();
            if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                return address;
            }
        }
        return null;
    }

    public static <T> List<T> converStringToList(String input, Class<T> elementType) {
        // Loại bỏ dấu ngoặc vuông từ đầu và cuối chuỗi
        String object = input.substring(1, input.length() - 1);
        // Tách chuỗi thành mảng các chuỗi roles, sử dụng dấu phẩy làm delimiter
        String[] arr = (object.replace("\"", "")).split(",");

        // Chuyển mảng thành danh sách
        List<T> lst = new ArrayList<>();
        for (String s : arr) {
            lst.add(elementType.cast(s.trim()));
        }
        return lst;
    }

    public static List<Integer> convertStringToListInteger(String input) {
        // Loại bỏ dấu ngoặc vuông từ đầu và cuối chuỗi
        String object = input.substring(1, input.length() - 1);
        // Tách chuỗi thành mảng các chuỗi roles, sử dụng dấu phẩy làm delimiter
        String[] arr = (object.replace("\"", "")).split(",");

        // Chuyển mảng thành danh sách
        List<Integer> lst = new ArrayList<>();
        for (String s : arr) {
            lst.add(Integer.parseInt(s.trim()));
        }
        return lst;
    }

    public static <S, T> List<T> toListObject(List<S> list, Class<T> targetClass) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        for (S item : list) {
            result.add(toObject(item, targetClass));
        }

        return result;
    }

    public static <S, T> T toObject(S s, Class<T> targetClass) {
        getMapper();
        if (s == null) {
            return null;
        }
        return mapper.map(s, targetClass);
    }

    private static void getMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String jsonString = "";
            try {
                jsonString = mapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                jsonString = "Can't build json from object";
            }
            return jsonString;
        }
    }

    public static Date str2Date(String strDate) {
        Date result = null;
        if (isNullOrEmpty(strDate) || strDate.equals("null")) {
            return result;
        }
        try {
            DateFormat df = null;
            if (strDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                df = new SimpleDateFormat("dd-MM-yyyy");
            } else if (strDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                df = new SimpleDateFormat(FORMAT_DDMMYYYY);
            } else if (strDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                df = new SimpleDateFormat(F_YYYYMMDD);
            } else if (strDate.matches("\\d{4}/\\d{2}/\\d{2}")) {
                df = new SimpleDateFormat("yyyy/MM/dd");
            } else if (strDate.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$")) {
                df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            } else if (strDate.matches("\\w{3} \\w{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2} \\w{3}\\+\\w{4}")) {
                df = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z");
            } else if (strDate.matches("\\w{3} \\w{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2} \\w{3}")) {
                df = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z");
            } else if (strDate.matches("\\w{3} \\w{3} \\d{2} \\d{2}:\\d{2}:\\d{2} GMT[+-]\\d{2}:\\d{2} \\d{4}$")) {
                df = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX yyyy");
            } else {
                df = new SimpleDateFormat(FORMAT_DDMMYYYY);
            }
            result = df.parse(strDate.trim());
        } catch (ParseException e) {
            return null;
        }

        return result;
    }

    public static String getStatus(String statusCode) {
        if (Constant.STATUS_STYLES.ACTIVE.equals(statusCode)) {
            return "ACTIVE";
        } else if (Constant.STATUS_STYLES.INACTIVE.equals(statusCode)) {
            return "INACTIVE";
        }
        return null;
    }

    public static String getImageBlank(String image) {
        if (isNullOrEmpty(image)) {
            return "../../../../assets/img/background/blank-image.svg";
        }
        return image;
    }

    public static String getStatusVn(String statusCode) {
        switch (statusCode) {
            case Constant.STATUS_STYLES.ACTIVE:
                return "Hoạt động";
            case Constant.STATUS_STYLES.INACTIVE:
                return "Ngừng hoạt động";
            case Constant.STATUS_STYLES.DELETED:
                return "Đã xóa";
            case Constant.STATUS_PAYMENT.PENDING:
                return "Chờ xác nhận";
            case Constant.STATUS_PAYMENT.SUCCESS:
                return "Hoàn thành";
            case Constant.STATUS_PAYMENT.CANCEL:
                return "Hủy";
            case Constant.STATUS_PAYMENT.REJECT:
                return "Từ chối";
            case Constant.STATUS_PAYMENT.FINISH:
                return "Đã giao hàng";
            case Constant.STATUS_PAYMENT.WAITING:
                return "Chờ giao hàng";
            case Constant.STATUS_PAYMENT.TRANSPORTING:
                return "Đang giao hàng";
            default:
                return null;
        }
    }

    public static String getPaymentStatus(String statusCode) {
        switch (statusCode) {
            case Constant.PAYMENT_STATUS.NOT_PAID:
                return "Chưa thanh toán";
            case Constant.PAYMENT_STATUS.PAID:
                return "Đã thanh toán";
            case Constant.PAYMENT_STATUS.SUCCESS:
                return "Thanh toán thành công";
            default:
                return null;
        }
    }

    public static String getPaymentMethod(String statusCode) {
        switch (statusCode) {
            case "M":
                return "Thanh toán khi nhận hàng";
            case "ONLINE":
                return "Thanh toán chuyển khoản";
            default:
                return null;
        }
    }

    public static String getCountryName(String countryCode) {
        switch (countryCode) {
            case "VN":
                return "Viet Nam";
            case "DE":
                return "Đức";
            case "US":
                return "Hoa Kí";
            case "JP":
                return "Nhật Bản";
            case "CN":
                return "Trung Quốc";
            default:
                return null;
        }
    }

    public static PaginationInfo getPaginationInfo(int pageNumber, int pageSize, int pageTotal) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setPageNumber(pageNumber);
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setPageTotal(pageTotal);
        int firstPage = pageNumber - 2;
        if (firstPage < 1) {
            firstPage = 1;
        }
        paginationInfo.setPageFirst(firstPage);
        int lastPage = firstPage + pageSize - 1;
        if (lastPage > pageTotal) {
            lastPage = pageTotal;
        }
        paginationInfo.setPageLast(lastPage);
        List<Integer> pages = new ArrayList<>();
        int totalPage = pageTotal / pageSize;
        if (pageTotal % pageSize != 0) {
            totalPage++;
        }
        for (int i = 0; i < totalPage; i++) {
            pages.add(i + 1);
        }
        paginationInfo.setPages(pages);
        paginationInfo.setPageCount(totalPage);
        return paginationInfo;
    }

    public static String date2Str(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(F_DDMMYYYY);
        return dateFormat.format(date);
    }

    public static String date2Str(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(F_DDMMYYYY_HHMM);
        return date.format(formatter);
    }

    public static String generateProductCode() {
        String code = "sku-";
        Long time = new Date().getTime();
        code += time.toString();
        return code;
    }

    public static String generatePaymentCode() {
        String code = "pay-";
        Long time = new Date().getTime();
        code += time.toString();
        return code;
    }

    public static String generateFileName() {
        String code = "file-";
        Long time = new Date().getTime();
        code += time.toString();
        return code;
    }


    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS;


    public static String generateRandomString(int number) {
        Random random = new Random();

        // Ensure at least one uppercase, one lowercase, and one digit
        StringBuilder sb = new StringBuilder();
        sb.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        sb.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // Fill the rest of the string with random characters
        for (int i = 3; i < number; i++) {
            sb.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        // Shuffle the characters to ensure randomness
        char[] result = sb.toString().toCharArray();
        for (int i = 0; i < result.length; i++) {
            int randomIndex = random.nextInt(result.length);
            char temp = result[i];
            result[i] = result[randomIndex];
            result[randomIndex] = temp;
        }

        return new String(result);
    }

    public static String convertRole(String roleName) {
        if ("USER_ROLE".equals(roleName)) {
            return "Khách hàng";
        }
        if ("ADMIN_ROLE".equals(roleName)) {
            return "Quản lý";
        }
        return "Unknown";

    }

    public static CustomerDto toCustomerDto(User user) {
        if (user == null) {
            return null;
        }

        CustomerDto dto = new CustomerDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setGender(user.getGender());
        dto.setNumberPhone(user.getNumberPhone());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDateString = formatter.format(user.getBirthOfDate());
        dto.setBirthOfDate(strDateString);

        // Assuming each user has one role, adjust as necessary
        String roleName = user.getRoles().stream().findFirst().map(Role::getName).orElse("Unknown");
        dto.setRole(convertRole(roleName));

        return dto;
    }

    public static EmployeeDTO toEmployeeDto(User user) {
        if (user == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setGender(user.getGender());
        dto.setNumberPhone(user.getNumberPhone());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDateString = formatter.format(user.getBirthOfDate());
        dto.setBirthOfDate(strDateString);

        // Assuming each user has one role, adjust as necessary
        String roleName = user.getRoles().stream().findFirst().map(Role::getName).orElse("Unknown");
        dto.setRole(convertRole(roleName));

        return dto;
    }

}