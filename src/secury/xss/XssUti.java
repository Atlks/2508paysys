package secury.xss;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.lang.reflect.Field;
import java.util.Set;

//encode html
public class XssUti {
    /**
     * 手动校验 实体对象，满足jakarta.validation约束
     * v25.89
     *
     * 正对每个str type fld，，def  默认检查不能包含<>&
     * @param dto
     */
    public static <T> void valdt(T dto) {


        containsXssChar4dto(dto);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            String errmsg = "";
            for (ConstraintViolation<T> violation : violations) {
                errmsg += ("PropertyPath=" + violation.getPropertyPath() + ",msg=" + violation.getMessage());
            }
            throw new IllegalArgumentException("无效参数，cls=" + dto.getClass() + "," + errmsg);
        }

    }

    /**
     * 循环 dto 字段，如果是字符串类型，检查不能包含  特殊符号，否则抛出异常
     * @param dto DTO 对象
     * @param <T> 泛型
     */
    public static <T> void containsXssChar4dto(T dto) {
        if (dto == null) return;

        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(dto);
                    if (value != null && containsXssChar(value)) {
                        throw new IllegalArgumentException("字段 [" + field.getName() + "] 包含非法字符：< > &");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("无法访问字段: " + field.getName(), e);
                }
            }
        }
    }

    /**
     * xss检测
     * 只能包含字母数字汉字，以及@_符号
     * @param value
     * @return
     */
    private static boolean containsXssChar(String value) {
        if (value == null) return false;
        // 正则说明：
        // \u4e00-\u9fa5 表示汉字
        // a-zA-Z 表示英文字母
        // 0-9 表示数字
        // @ 和 _ 是允许的特殊字符
        return value.matches("^[a-zA-Z0-9\u4e00-\u9fa5@_]+$");
    }
}
