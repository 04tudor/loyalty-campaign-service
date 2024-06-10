package md.maib.retail.persistence;

public class LoyaltyEventTypesAdapterSingleton {
    private static LoyaltyEventTypesAdapter instance;

    private LoyaltyEventTypesAdapterSingleton() {
    }

    public static LoyaltyEventTypesAdapter getInstance() {
        if (instance == null) {
            instance = new LoyaltyEventTypesAdapter();
        }
        return instance;
    }

    public static void setInstance(LoyaltyEventTypesAdapter adapter) {
        instance = adapter;
    }
}
