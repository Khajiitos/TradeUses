package me.khajiitos.tradeuses.common.config;

import joptsimple.internal.Strings;
import me.khajiitos.tradeuses.common.TradeUses;
import net.minecraft.client.resources.language.I18n;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static final File file = new File("config/tradeuses_text.txt");
    public static List<String> lines = new ArrayList<>();

    public static List<String> getDisplayLines() {
        List<String> configLines = lines;
        if (configLines.isEmpty()) {
            return List.of(I18n.get("tradeuses.default_tooltip").split("\n"));
        } else {
            return lines;
        }
    }

    public static void load() {
        if (file.exists()) {
            lines.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                reader.lines().forEach(lines::add);
            } catch (IOException e) {
                TradeUses.LOGGER.error("Failed to read tradeuses_text.txt", e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {
            try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
                fileWriter.write(Strings.join(lines, "\n"));
            } catch (IOException e) {
                TradeUses.LOGGER.error("Failed to save tradeuses_text.txt", e);
            }
        }
    }
}
