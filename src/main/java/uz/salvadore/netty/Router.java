package uz.salvadore.netty;

import io.netty.handler.codec.http.*;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import uz.salvadore.netty.annotations.RequestMapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Router {

    public static FullHttpResponse routes(final FullHttpRequest request) throws Exception {
        final HttpMethod method = method(request);
        final String ctrlName = ctrlName(request);
        final String methodName = methodName(request);
        //searchCtrlAndMethod(ctrlName, methodName);

        String className = getClassName(ctrlName);

        Class<?> clazz = Class.forName(className);
        Method m = clazz.getDeclaredMethod(methodName, new Class[]{FullHttpRequest.class});
        Object o = clazz.newInstance();
        FullHttpResponse response = (FullHttpResponse) m.invoke(o, request);

        return response;
    }

    private static Method getMethod(String className, String methodName) throws Exception {
        Reflections ref = new Reflections(className, new MethodAnnotationsScanner());
        Set<Method> methods = ref.getMethodsAnnotatedWith(RequestMapping.class);
        Method m = methods.stream().filter(i -> i.getName().equals(methodName)).findFirst().orElse(null);
        if (null == m)
            throw new Exception("Error");
        return m;
    }

    private static String getClassName(String ctrlName) throws Exception {
        String className = ApplicationContext.getBeans()
                .stream()
                .filter(i -> {
                    String[] ctrs = i.toLowerCase().split("\\.");
                    if (ctrs[ctrs.length - 1].equals(ctrlName))
                        return true;
                    return false;
                }).findFirst().orElse("");
        if (className.equals(""))
            throw new Exception("Error");
        return className;
    }

    private static HttpMethod method(final FullHttpRequest request) {
        return request.method();
    }

    private static String ctrlName(final FullHttpRequest request) {
        return request.uri().split("/")[1].concat("controller");
    }

    private static String methodName(final FullHttpRequest request) {
        return request.uri().split("/")[2];
    }

    /*private static void searchCtrlAndMethod(String ctrl, String method) throws Exception {
        Set<Map.Entry<String, List<String>>> entries = ApplicationContext.getBeans().entrySet();
        String ctrlName;
        String ctrlName = ctrl.concat("controller");
        for (Map.Entry<String, List<String>> entry : entries) {
            String[] ctrls = entry.getKey().split("\\.");
            String ct = ;
            if (ctrls[ctrls.length - 1].toLowerCase().equals(ctrl.concat("controller"))) {
                ctrlName =
            }
            System.out.println(ct);
        }
        Map.Entry<String, List<String>> map = entries
                .stream()
                .filter(i -> i.getKey().toLowerCase().contains(ctrlName))
                .findFirst().orElse(null);
        if (null == map)
            throw new Exception("Error");
        Object m = map.getValue().stream().filter(i -> i.equals(method)).findFirst().orElse(null);
        if (m == null)
            throw new Exception("Error");
    }*/

}
