package entities;

import java.util.HashMap;

public class SubscriptionBudget extends Budget {
    public SubscriptionBudget(int maxSpend, int timeSpan) {
        super(new String[]{"Movies", "TV"}, maxSpend, timeSpan);
    }
}
