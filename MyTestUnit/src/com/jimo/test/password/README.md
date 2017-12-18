# 判断用例
内容来自Thinking in Java

### UseCase注解
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    public int id();

    public String desc() default "no description";
}
```

### 使用
```java
public class PasswordUtils {

    @UseCase(id = 10, desc = "password must contain at least one number")
    public boolean validatePassword(String password) {
        return password.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 11)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 12, desc = "new password cannot equal previously used ones")
    public boolean checkForNewPassword(List<String> oldPasswords, String newPassword) {
        return !oldPasswords.contains(newPassword);
    }
}
```

### 测试
```java
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
```