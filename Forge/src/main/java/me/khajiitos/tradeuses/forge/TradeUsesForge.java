package me.khajiitos.tradeuses.forge;

import me.khajiitos.tradeuses.common.TradeUses;
import me.khajiitos.tradeuses.common.screen.ConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

@Mod(TradeUses.MOD_ID)
public class TradeUsesForge {
    public TradeUsesForge() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            TradeUses.init();
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((Function<Screen, Screen>) ConfigScreen::new));
        });
    }
}