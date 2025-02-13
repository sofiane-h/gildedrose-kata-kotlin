package com.gildedrose.model

import com.gildedrose.Item
import com.gildedrose.QualityUpdater

class AgedBrieItem(
    override val item: Item
) : InventoryItem, QualityUpdater {

    override fun updateQuality() {
        item.quality = if (item.sellIn >= 0) {
            item.quality increaseBy 1
        } else {
            item.quality increaseBy 2
        }
    }

    override fun updateSellIn() {
        item.sellIn -= 1
    }

    override fun toString(): String {
        return item.toString()
    }
}
