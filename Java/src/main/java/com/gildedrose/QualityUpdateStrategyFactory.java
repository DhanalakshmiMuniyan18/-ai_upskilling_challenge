package com.gildedrose;

import com.gildedrose.strategies.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * Factory class for creating quality update strategies based on item type.
 * Uses a map to cache strategy instances for better performance.
 * 
 * @author Saravanamuthukumar S
 */
public class QualityUpdateStrategyFactory {
    
    private static final Map<ItemType, QualityUpdateStrategy> STRATEGY_CACHE = new EnumMap<>(ItemType.class);
    
    static {
        // Initialize strategy cache
        STRATEGY_CACHE.put(ItemType.REGULAR, new RegularItemStrategy());
        STRATEGY_CACHE.put(ItemType.AGED_BRIE, new AgedBrieStrategy());
        STRATEGY_CACHE.put(ItemType.BACKSTAGE_PASS, new BackstagePassStrategy());
        STRATEGY_CACHE.put(ItemType.SULFURAS, new SulfurasStrategy());
        STRATEGY_CACHE.put(ItemType.CONJURED, new ConjuredItemStrategy());
    }
    
    /**
     * Creates or retrieves the appropriate quality update strategy for the given item.
     * 
     * @param item the item to create a strategy for
     * @return the appropriate quality update strategy
     */
    public static QualityUpdateStrategy createStrategy(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        
        ItemType itemType = ItemType.fromName(item.getName());
        return STRATEGY_CACHE.get(itemType);
    }
    
    /**
     * Creates or retrieves the appropriate quality update strategy for the given item type.
     * 
     * @param itemType the type of item
     * @return the appropriate quality update strategy
     */
    public static QualityUpdateStrategy createStrategy(ItemType itemType) {
        if (itemType == null) {
            throw new IllegalArgumentException("ItemType cannot be null");
        }
        
        return STRATEGY_CACHE.get(itemType);
    }
} 