package com.gildedrose

import com.gildedrose.model.InventoryItem

class GildedRose(private val items: List<InventoryItem>) {

    fun updateQuality() {
        items.forEach {
            it.updateSellIn()
            it.updateQuality()
        }
    }
}
