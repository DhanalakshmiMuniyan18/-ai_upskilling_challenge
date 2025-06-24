package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing different types of items in the Gilded Rose inventory system.
 * Each item type has specific quality update behavior.
 * 
 * @author Saravanamuthukumar S
 */
public enum ItemType {
    
    /**
     * Regular items that degrade in quality over time
     */
    REGULAR("Regular Item"),
    
    /**
     * Aged Brie - increases in quality as it gets older
     */
    AGED_BRIE("Aged Brie"),
    
    /**
     * Backstage passes - quality increases as concert approaches, drops to 0 after
     */
    BACKSTAGE_PASS("Backstage passes to a TAFKAL80ETC concert"),
    
    /**
     * Sulfuras - legendary item that never changes
     */
    SULFURAS("Sulfuras, Hand of Ragnaros"),
    
    /**
     * Conjured items - degrade twice as fast as regular items
     */
    CONJURED("Conjured");
    
    private final String displayName;
    
    // Static map for efficient lookup by display name
    private static final Map<String, ItemType> DISPLAY_NAME_MAP = new HashMap<>();
    
    static {
        for (ItemType itemType : ItemType.values()) {
            DISPLAY_NAME_MAP.put(itemType.getDisplayName(), itemType);
        }
    }
    
    ItemType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Determines the item type based on the item name.
     * 
     * @param itemName the name of the item
     * @return the corresponding ItemType
     */
    public static ItemType fromName(String itemName) {
        if (itemName == null) {
            return REGULAR;
        }
        
        // First try exact match
        ItemType exactMatch = DISPLAY_NAME_MAP.get(itemName);
        if (exactMatch != null) {
            return exactMatch;
        }
        
        // Check for conjured items (case-insensitive partial match)
        if (itemName.toLowerCase().contains("conjured")) {
            return CONJURED;
        }
        
        return REGULAR;
    }
} 