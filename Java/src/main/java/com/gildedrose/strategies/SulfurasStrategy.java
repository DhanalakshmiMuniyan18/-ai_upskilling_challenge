package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for Sulfuras items that never change in quality or sell-in date.
 * Sulfuras is a legendary item with fixed quality of 80.
 * 
 * @author Saravanamuthukumar S
 */
public class SulfurasStrategy extends AbstractQualityUpdateStrategy {
    
    @Override
    public void updateQuality(Item item) {
        // Sulfuras never changes in quality
        // Quality is set to legendary value if not already set
        if (item.quality != SULFURAS_QUALITY) {
            item.quality = SULFURAS_QUALITY;
        }
    }
    
    @Override
    public void updateSellIn(Item item) {
        // Sulfuras never changes in sell-in date
        // Do nothing
    }
    
    @Override
    public void handleExpiredItem(Item item) {
        // Sulfuras never changes, even when expired
        // Do nothing
    }
} 