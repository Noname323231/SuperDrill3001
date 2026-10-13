package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMod implements ModInitializer
{
	public static final String MOD_ID = "template-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ResourceLocation drillSoundId = new ResourceLocation(MOD_ID, "drill_activate");
    public static final SoundEvent drillActivate = Registry.register(
            BuiltInRegistries.SOUND_EVENT,
            drillSoundId,
            SoundEvent.createVariableRangeEvent(drillSoundId)
    );

	@Override
	public void onInitialize() {
        System.out.print("Среда разработки готова\n Титов А.А. , Шафеев Р.Р.\n Название команды: 1000 и 1 костыль");
		LOGGER.info("Проект загружен успешно");

		ModItems.initialize();

        UseItemCallback.EVENT.register((player, level, interactionHand) ->
        {
            ItemStack stack  = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.getItem() == ModItems.HEAVY_DRILL)
            {

                if (!level.isClientSide())
                {
                    CompoundTag nbt = stack.getOrCreateTag();
                    boolean isActive = !nbt.getBoolean("Active");
                    nbt.putBoolean("Active", isActive);

                    String status = isActive ? "§aВКЛЮЧЕН" : "§cВЫКЛЮЧЕН";
                    player.sendSystemMessage(Component.literal("Режим работы бура изменен на: " + status));

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            drillActivate,
                            SoundSource.PLAYERS,
                            1.0F,
                            isActive ? 1.2F : 0.8F
                    );
                }

                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }

            return InteractionResultHolder.pass(stack);
        });
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
