package me.khajiitos.tradeuses.common;

import joptsimple.internal.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static final File file = new File("config/tradeuses_text.txt");
    public static List<String> lines = new ArrayList<>();

    static {
        lines.add("§lUses left: §r{uses_left}");
    }

    public static void load() {
        if (file.exists()) {
            lines.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(Strings.join(lines, "\n"));
            } catch (IOException e) {
                TradeUses.LOGGER.error("Failed to save tradeuses_text.txt", e);
            }
        }
    }
}
