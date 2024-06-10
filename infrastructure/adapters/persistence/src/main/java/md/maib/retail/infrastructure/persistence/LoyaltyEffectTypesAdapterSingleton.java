package md.maib.retail.infrastructure.persistence;

public class LoyaltyEffectTypesAdapterSingleton {
    private static LoyaltyEffectTypesAdapter instance;

    private LoyaltyEffectTypesAdapterSingleton() {
    }

    public static LoyaltyEffectTypesAdapter getInstance() {
        if (instance == null) {
            instance = new LoyaltyEffectTypesAdapter();
        }
        return instance;
    }

    public static void setInstance(LoyaltyEffectTypesAdapter adapter) {
        instance = adapter;
    }
}
