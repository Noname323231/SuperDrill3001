package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
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

                    if (isActive == true)
                    {
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

                }

                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }

            return InteractionResultHolder.pass(stack);
        });

        AttackBlockCallback.EVENT.register((player, level, interactionHand, pos, direction) ->
        {
            ItemStack stack  = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.getItem() == ModItems.HEAVY_DRILL) {
                CompoundTag nbt = stack.getOrCreateTag();
                boolean isActive = nbt.getBoolean("Active");

                if (isActive && !level.isClientSide()) {
                    for (int x = -5; x <= 5; x++) {
                        for (int y = -5; y <= 5; y++) {
                            BlockPos targetPos;

                            if (direction.getAxis() == Direction.Axis.Z) {
                                targetPos = pos.offset(x, y, 0);
                            } else if (direction.getAxis() == Direction.Axis.X) {
                                targetPos = pos.offset(0, y, x);
                            } else {
                                targetPos = pos.offset(x, 0, y);
                            }

                            level.destroyBlock(targetPos, true, player);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
