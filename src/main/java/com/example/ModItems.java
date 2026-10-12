package com.example;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class ModItems {

    public static final Item HEAVY_DRILL = Registry.register(
            BuiltInRegistries.ITEM,
            new ResourceLocation("template-mod", "heavy_drill"),
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .durability(10000)
                    .fireResistant()
                    )
    );


    public static void initialize()
    {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.accept(HEAVY_DRILL);
        });
    }
}
