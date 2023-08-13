package me.khajiitos.tradeuses.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeUses {
    public static final String MOD_ID = "tradeuses";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        Config.load();
    }
}
