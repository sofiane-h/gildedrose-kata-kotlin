package com.gildedrose.model

import com.gildedrose.Item

interface InventoryItem {

    val item: Item

    fun updateQuality()
    fun updateSellIn()
}
