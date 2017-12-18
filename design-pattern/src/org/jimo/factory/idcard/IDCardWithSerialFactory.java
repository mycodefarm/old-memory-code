package org.jimo.factory.idcard;

import org.jimo.factory.framework.Factory;
import org.jimo.factory.framework.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17-6-11.
 */
public class IDCardWithSerialFactory extends Factory {

    private Map<Integer, String> cards = new HashMap<>();
    private int serial = 1000;

    @Override
    protected synchronized Product createProduct(String owner) {
        return new IDCardWithSerial(owner, serial++);
    }

    @Override
    protected void registerProduct(Product p) {
        IDCardWithSerial cardWithSerial = (IDCardWithSerial) p;
        cards.put(cardWithSerial.getSerial(), cardWithSerial.getOwner());
    }
}
