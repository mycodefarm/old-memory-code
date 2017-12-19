package org.jimo.factory.idcard;

import org.jimo.factory.framework.Factory;
import org.jimo.factory.framework.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-6-11.
 */
public class IDCardFactory extends Factory {

    private List<String> idCards = new ArrayList<>();

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product p) {
        idCards.add(((IDCard) p).getOwner());
    }
}
