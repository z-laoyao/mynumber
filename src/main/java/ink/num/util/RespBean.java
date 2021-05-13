package ink.num.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RespBean<T> {
    public static String ALARM = "50";
    private String code;
    private String msg;
    private Object data;
    private String alarm = ALARM;
    public static String verificationCode;


}
