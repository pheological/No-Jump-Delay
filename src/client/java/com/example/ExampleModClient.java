package com.example;

import net.fabricmc.api.ClientModInitializer;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

@Environment(EnvType.CLIENT)
public class ExampleModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("nojumpdelay");
    private static final Random RANDOM = new Random();
    private int ticks = 0;
    private boolean enabled = true; // Flag to enable/disable the mod

    @Override
    public void onInitializeClient() {
        // Register the /nojumpdelay command
        ClientCommandManager.DISPATCHER.register(literal("nojumpdelay").executes(context -> {
            enabled = !enabled;
            context.getSource().sendFeedback(() -> "No Jump Delay mod " + (enabled ? "enabled" : "disabled"));
            return 1;
        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            
            if (!enabled || (client.getCurrentServerEntry() != null && "hoplite.net".equals(client.getCurrentServerEntry().address))) { //Makes it not work for hoplite as I can confirm it's not allowed
                return; //DM Pheological on discord if you want me to blacklist your server from this
            }

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
