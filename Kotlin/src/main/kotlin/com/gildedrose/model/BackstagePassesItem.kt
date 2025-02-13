package com.gildedrose.model

import com.gildedrose.Item
import com.gildedrose.QualityUpdater

class BackstagePassesItem(
    override val item: Item
) : InventoryItem, QualityUpdater {

    override fun updateQuality() {
        item.quality = when {
            item.sellIn > 10 -> item.quality increaseBy 1
            item.sellIn in 10 downTo 6 -> item.quality increaseBy 2
            item.sellIn in 5 downTo 0 -> item.quality increaseBy 3
            item.sellIn < 0 -> 0
            else -> item.quality
        }
    }

    override fun updateSellIn() {
        item.sellIn -= 1
    }

    override fun toString(): String {
        return item.toString()
    }
}
