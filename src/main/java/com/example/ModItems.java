package com.example;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class ModItems {

    public static final Item HEAVY_DRILL = Registry.register(
            BuiltInRegistries.ITEM,
            new ResourceLocation("template-mod", "heavy_drill"),
            new Item(new Item.Properties().stacksTo(1))
    );

    public static void initialize() {
    }
}
