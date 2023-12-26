package me.khajiitos.tradeuses.neoforged;

import me.khajiitos.tradeuses.common.TradeUses;
import me.khajiitos.tradeuses.common.screen.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler;

@Mod(TradeUses.MOD_ID)
public class TradeUsesNeoforged {
    public TradeUsesNeoforged() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            TradeUses.init();
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(ConfigScreen::new));
        });
    }
}