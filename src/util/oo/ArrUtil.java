package util.oo;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.*;

// static util.tx.dbutil.getstartPosition;

public class ArrUtil {
    public static void pushSet(Map<String, Set<Method>> mapCls, String pathKey, Method m) {
        Set<Method> st = mapCls.get(pathKey);
        if (st == null) {
            st = new HashSet<>();

        }
        st.add(m);
        mapCls.put(pathKey, st);

    }

    public static void pushSet(Map<Class, Set<Method>> mapCls, Class keyClz, Method m) {
        Set<Method> st = mapCls.get(keyClz);
        if (st == null) {
            st = new HashSet<>();

        }
        st.add(m);
        mapCls.put(keyClz, st);

    }

    public static <T> List<T> subList2025(List<T> list1, int pageNumber, int pageSize) {
        return null;
    }

    private static int getstartPosition(int pageNumber, int pageSize) {

    return  0;}

    public static int getEndidx(int pageSize, int fromIndex, int sizeList) {
        int endidx = fromIndex + pageSize;
        if (endidx > (sizeList - 1)) {
            endidx = sizeList;
            //  List<T> listPageed = list1.subList(fromIndex, endidx);
            //  return new PageResult<>(listPageed, totalRecords, totalPages);
        }
        return endidx;
    }


    /**
     * 使用 SpEL 表达式对列表进行排序...
     *
     * @param list       需要排序的集合
     * @param expression 比较表达式，例如：#this['age'] > #this['age']
     * @return 排序后的集合
     */
//    public static List<SortedMap<String, Object>> sortWithSpEL(List<SortedMap<String, Object>> list, String expression) {
//        ExpressionParser parser = new SpelExpressionParser();
//
//        // 使用 Comparator 比较表达式排序
//        list.sort((map1, map2) -> {
//            StandardEvaluationContext context1 = new StandardEvaluationContext();
//            StandardEvaluationContext context2 = new StandardEvaluationContext();
//
//            context1.setRootObject(map1);
//            context2.setRootObject(map2);
//
//            // 设置 map1 和 map2 为上下文变量
//         //   context.setVariable("map1", map1);
//         //   context.setVariable("map2", map2);
//
//            try {
//                // 将 map1 和 map2 传入表达式并计算结果
//                Boolean result = parser.parseExpression(expression).getValue(context1, Boolean.class);
//                int rtl = result != null && result ? -1 : 1;
//                System.out.println("sortWithSpEL().rtl="+rtl);
//                return rtl;
//            } catch (Exception e) {
//                System.err.println("Error evaluating expression: " + e.getMessage());
//                return 0; // 如果表达式解析失败，保持原顺序
//            }
//        });
//
//        return list;
//    }
//


}
