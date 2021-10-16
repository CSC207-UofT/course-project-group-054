package entities;

/**
 * A Budget for subscriptions to streaming services. The categories are "Movies" and "TV".
 */
public class SubscriptionBudget extends Budget {
    /**
     * Construct a new SubscriptionBudget with the given limit on time span.
     *
     * @param maxSpend the maximum amount of money that can be spent on items in this budget in timeSpan days
     * @param timeSpan the amount of time, in days, for which the budget is active and the limit on spending given by
     *                 maxSpend applies
     */
    public SubscriptionBudget(int maxSpend, int timeSpan) {
        super(new String[]{"Movies", "TV"}, maxSpend, timeSpan);
    }
}
