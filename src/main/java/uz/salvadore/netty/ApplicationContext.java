package uz.salvadore.netty;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import uz.salvadore.netty.annotations.RestController;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {

    private static final List<String> beans = new ArrayList<>();
    private static ApplicationContext instance;

    private ApplicationContext() {
        fill();
    }

    private void fill() {
        Reflections ref = new Reflections("uz.salvadore.netty.controllers");
        for (Class<?> cl : ref.getTypesAnnotatedWith(RestController.class)) {
            beans.add(cl.getName());
        }
    }

    public static List<String> getBeans() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return beans;
    }



}
