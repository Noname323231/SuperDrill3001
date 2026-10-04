package com.example;

import net.fabricmc.api.ModInitializer;

import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMod implements ModInitializer {
	public static final String MOD_ID = "template-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        System.out.print("Среда разработки готова\n Титов А.А. , Шафеев Р.Р.\n Название команды: 1000 и 1 костыль");
		LOGGER.info("Проект загружен успешно");
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
