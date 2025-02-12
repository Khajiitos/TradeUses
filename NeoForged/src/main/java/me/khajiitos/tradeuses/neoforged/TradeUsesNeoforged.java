package me.khajiitos.tradeuses.neoforged;

import me.khajiitos.tradeuses.common.TradeUses;
import me.khajiitos.tradeuses.common.screen.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(TradeUses.MOD_ID)
public class TradeUsesNeoforged {
    public TradeUsesNeoforged(ModContainer modContainer) {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            TradeUses.init();
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> new ConfigScreen(parent));
        }
    }
}