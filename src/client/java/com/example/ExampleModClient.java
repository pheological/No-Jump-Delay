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
    private static final Random RANDOM = new Random();
    private int ticks = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || !client.player.isOnGround()) {
                return;
            }

            ticks++;

            if (ticks > RANDOM.nextInt(3) + 1 && client.options.jumpKey.isPressed()) {
                client.player.jump();
                ticks = 0;
            }
        });
    }
}
