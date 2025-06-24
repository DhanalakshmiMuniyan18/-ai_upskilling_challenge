package com.gildedrose;

/**
 * Abstract base class for quality update strategies.
 * Provides common functionality and constants used by all strategies.
 * 
 * @author Saravanamuthukumar S
 */
public abstract class AbstractQualityUpdateStrategy implements QualityUpdateStrategy {
    
    /**
     * Maximum quality value for most items
     */
    protected static final int MAX_QUALITY = 50;
    
    /**
     * Minimum quality value
     */
    protected static final int MIN_QUALITY = 0;
    
    /**
     * Quality increase/decrease amount
     */
    protected static final int QUALITY_CHANGE = 1;
    
    /**
     * Backstage pass quality increase threshold (10 days)
     */
    protected static final int BACKSTAGE_PASS_THRESHOLD_1 = 10;
    
    /**
     * Backstage pass quality increase threshold (5 days)
     */
    protected static final int BACKSTAGE_PASS_THRESHOLD_2 = 5;
    
    /**
     * Sulfuras legendary quality value
     */
    protected static final int SULFURAS_QUALITY = 80;
    
    /**
     * Default sell-in decrease amount
     */
    protected static final int SELL_IN_DECREASE = 1;
    
    /**
     * Increases the quality of an item, respecting the maximum quality limit.
     * 
     * @param item the item to increase quality for
     * @param amount the amount to increase by
     */
    protected void increaseQuality(Item item, int amount) {
        int newQuality = item.getQuality() + amount;
        if (newQuality > MAX_QUALITY) {
            item.setQuality(MAX_QUALITY);
        } else {
            item.setQuality(newQuality);
        }
    }
    
    /**
     * Decreases the quality of an item, respecting the minimum quality limit.
     * 
     * @param item the item to decrease quality for
     * @param amount the amount to decrease by
     */
    protected void decreaseQuality(Item item, int amount) {
        int newQuality = item.getQuality() - amount;
        if (newQuality < MIN_QUALITY) {
            item.setQuality(MIN_QUALITY);
        } else {
            item.setQuality(newQuality);
        }
    }
    
    /**
     * Sets the quality of an item to zero.
     * 
     * @param item the item to set quality for
     */
    protected void setQualityToZero(Item item) {
        item.setQuality(MIN_QUALITY);
    }
    
    /**
     * Checks if an item has expired (sell-in date is negative).
     * 
     * @param item the item to check
     * @return true if the item has expired
     */
    protected boolean isExpired(Item item) {
        return item.getSellIn() < 0;
    }
    
    /**
     * Decreases the sell-in date by the default amount.
     * 
     * @param item the item to update
     */
    @Override
    public void updateSellIn(Item item) {
        item.setSellIn(item.getSellIn() - SELL_IN_DECREASE);
    }
} 