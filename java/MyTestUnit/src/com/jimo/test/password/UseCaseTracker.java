package com.jimo.test.password;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jimo on 17-8-27.
 */
public class UseCaseTracker {

    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found case : " + uc.id() + " " + uc.desc());
                useCases.remove(new Integer(uc.id()));
            }
        }
        for (int i : useCases) {
            System.out.println("Warning: Missing case-" + i);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases, 10, 11, 12, 100);
        trackUseCases(useCases, PasswordUtils.class);
        /**
         Found case : 10 password must contain at least one number
         Found case : 11 no description
         Found case : 12 new password cannot equal previously used ones
         Warning: Missing case-100
         */
    }
}
