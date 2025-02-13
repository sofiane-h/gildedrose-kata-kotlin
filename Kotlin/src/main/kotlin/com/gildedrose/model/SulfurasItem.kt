package com.gildedrose.model

import com.gildedrose.Item

class SulfurasItem(
    override val item: Item
) : InventoryItem {

    override fun updateQuality() {
        item.quality = 80
    }

    override fun updateSellIn() {
    }

    override fun toString(): String {
        return item.toString()
    }
}
