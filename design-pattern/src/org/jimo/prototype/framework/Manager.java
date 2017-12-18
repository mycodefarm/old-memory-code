package org.jimo.prototype.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-6-15.
 * Manager负责将通过register注册的对象通过create方法获取其克隆对象
 */
public class Manager {
    private Map<String, Product> showCase = new HashMap<>();

    public void register(String name, Product proto) {
        showCase.put(name, proto);
    }

    public Product create(String protoName) {
        Product product = showCase.get(protoName);
        return product.createClone();
    }
}
