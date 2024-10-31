package com.example;

import net.fabricmc.api.ClientModInitializer;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {
public static final Logger LOGGER = LoggerFactory.getLogger("nojumpdelay");
	int ticks = 0;

	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			Random random = new Random();
			++this.ticks;
			MinecraftClient mc = MinecraftClient.getInstance();
			if (mc.player != null) {
				int randomNumber = random.nextInt(3) + 1;
				if (this.ticks > randomNumber) {
					if (mc != null && mc.player != null && client.player != null && mc.options.jumpKey.isPressed() && mc.player.isOnGround() && !mc.player.isCreative() && !mc.player.isSpectator()) {
						mc.player.jump();
					}

					this.ticks = 0;
				}
			}

		});
	}
}
