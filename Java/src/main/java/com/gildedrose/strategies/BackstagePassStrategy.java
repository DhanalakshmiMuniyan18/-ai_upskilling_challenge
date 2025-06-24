package com.gildedrose.strategies;

import com.gildedrose.AbstractQualityUpdateStrategy;
import com.gildedrose.Item;

/**
 * Strategy for Backstage Pass items that increase in quality as the concert approaches.
 * Quality increases by 1 normally, by 2 when 10 days or less, by 3 when 5 days or less,
 * and drops to 0 after the concert.
 * 
 * @author Saravanamuthukumar S
 */
public class BackstagePassStrategy extends AbstractQualityUpdateStrategy {
    
    @Override
    public void updateQuality(Item item) {
        increaseQuality(item, QUALITY_CHANGE);
        
        // Additional quality increase when 10 days or less
        if (item.sellIn <= BACKSTAGE_PASS_THRESHOLD_1) {
            increaseQuality(item, QUALITY_CHANGE);
        }
        
        // Additional quality increase when 5 days or less
        if (item.sellIn <= BACKSTAGE_PASS_THRESHOLD_2) {
            increaseQuality(item, QUALITY_CHANGE);
        }
    }
    
    @Override
    public void handleExpiredItem(Item item) {
        // Quality drops to 0 after the concert
        setQualityToZero(item);
    }
} 