package me.khajiitos.tradeuses.neoforged;

import me.khajiitos.tradeuses.common.TradeUses;
import me.khajiitos.tradeuses.common.screen.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = TradeUses.MOD_ID, dist = Dist.CLIENT)
public class TradeUsesNeoforged {
    public TradeUsesNeoforged(ModContainer modContainer) {
        TradeUses.init();
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, parent) -> new ConfigScreen(parent));
    }
}