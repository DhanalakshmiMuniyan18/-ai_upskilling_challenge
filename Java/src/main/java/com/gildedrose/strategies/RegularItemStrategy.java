package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for regular items that degrade in quality over time.
 * Quality decreases by 1 each day, and by 2 after the sell-by date.
 * 
 * @author Saravanamuthukumar S
 */
public class RegularItemStrategy extends AbstractQualityUpdateStrategy {
    
    @Override
    public void updateQuality(Item item) {
        decreaseQuality(item, QUALITY_CHANGE);
    }
    
    @Override
    public void handleExpiredItem(Item item) {
        decreaseQuality(item, QUALITY_CHANGE);
    }
} 