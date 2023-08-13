package me.khajiitos.tradeuses.fabric;

import me.khajiitos.tradeuses.common.TradeUses;
import net.fabricmc.api.ClientModInitializer;

public class TradeUsesFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TradeUses.init();
    }
}
