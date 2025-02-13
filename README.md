# Gilded Rose Refactoring Kata

This project is my solution of the Gilded Rose Refactoring Kata. 
I have made it in Kotlin, this is why I just cloned the original [GitHub project](https://github.com/emilybache/GildedRose-Refactoring-Kata) and only keep the kotlin files and tests related stuff.

## Requirements
See [Gilded Rose Requirements Specification](GildedRoseRequirements.md) file for more information.

## Solution
### Understand the existing code
Firstly, I took a look at the `GildedRose` with its _messy_ `updateQuality()` function.
There were a lot of nested if/else, so to clarify everything and be sure that I didn't miss anything, I decided to add some comments on almost each condition.
This way, I can have a better understanding of what this function do and if the logic follows the requirements.

### Unit Tests
Then, I added some unit test, just to be sure that I will not break anything when I will do my refactoring.
I also added some tests for the new Conjured item which will at first fail as the logic has not been implemented yet.

### Refactoring
The next step is the actual refactoring.
In my point of view, it was pretty clear that each item has their own strategy to update their quality value.
This is why I decided to go for a strategy design pattern _-like_.

Thus, I created an `InventoryItem` which has an `Item` and two functions to update its `quality` and `sellIn` value.
Each item of the inventory extends this interface and provides their own update strategy by using the `QualityUpdater` interface which ensure to keep the quality value between a minimum and a maximum.

This way, to update the quality of the items we only need to iterate over the list of `ItemInventory` and call `updateQuality()` and `updateSellIn()`
```koltin
items.forEach {
    it.updateSellIn()
    it.updateQuality()
}
```

### Advantages
- More future-proof as it is easier to add new item logic;
- Clear vision of each item's update strategy;
- Each item is now typed, which makes it easier to identify; 
- Minimum and maximum range for the update value is encapsulated which makes the code easier to read and simplify the logic within each item's `updateQuality()` function.

### Disadvantages
- If we need to create new `Item` type, we need to create a new object model and not only create a generic `Item` object.
- There is no rule for the item naming, meaning that a `ClassicItem` can have an item named "AgedBrie", which can be confusing:
```kotlin
val item = ClassicItem(Item(name = "AgedBrie", sellIn = 10, quality = 10))
```

## Backstage item issue
There is, in my point of view, something not clear about the "Backstage passes" item regarding its requirement.
If we look at the [GildedRoseRequirements](GildedRoseRequirements.md) we can read:
>"Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
> - Quality increases by 2 when there are **10 days or less** and by 3 when there are 5 days or less but
> - Quality drops to 0 after the concert

Which would means that if we reach the sellIn value `10`, we should increase its value by 2.

So for example between the day 4 and the day 6 we would have:
```
- Day 4: Backstage passes to a TAFKAL80ETC concert, 11, 24
- Day 5: Backstage passes to a TAFKAL80ETC concert, 10, 26
- Day 6: Backstage passes to a TAFKAL80ETC concert, 9, 28
```

But, on the `stdout.gr`, we have:
```
- Day 4: Backstage passes to a TAFKAL80ETC concert, 11, 24
- Day 5: Backstage passes to a TAFKAL80ETC concert, 10, 25
- Day 6: Backstage passes to a TAFKAL80ETC concert, 9, 27
```
If the `stdout.gr` is right, then I feel like the requirements should say "9 days or less".

This results to a failure in my `TexttestFixture`, obviously.
It is pretty easy to fix if we confirm that the `stdout.gr` is right, but I decided to keep it as it and start a discussion on the [Kata's GitHub](https://github.com/emilybache/GildedRose-Refactoring-Kata/issues/593).
