package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for Conjured items that degrade in quality twice as fast as regular items.
 * Quality decreases by 2 each day, and by 4 after the sell-by date.
 * 
 * @author Saravanamuthukumar S
 */
public class ConjuredItemStrategy extends AbstractQualityUpdateStrategy {
    
    private static final int CONJURED_QUALITY_DECREASE = QUALITY_CHANGE * 2;
    
    @Override
    public void updateQuality(Item item) {
        decreaseQuality(item, CONJURED_QUALITY_DECREASE);
    }
    
    @Override
    public void handleExpiredItem(Item item) {
        decreaseQuality(item, CONJURED_QUALITY_DECREASE);
    }
} 