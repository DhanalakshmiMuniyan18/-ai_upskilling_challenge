package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for Aged Brie items that increase in quality over time.
 * Quality increases by 1 each day, and by 2 after the sell-by date.
 * 
 * @author Saravanamuthukumar S
 */
public class AgedBrieStrategy extends AbstractQualityUpdateStrategy {
    
    @Override
    public void updateQuality(Item item) {
        increaseQuality(item, QUALITY_CHANGE);
    }
    
    @Override
    public void handleExpiredItem(Item item) {
        increaseQuality(item, QUALITY_CHANGE);
    }
} 