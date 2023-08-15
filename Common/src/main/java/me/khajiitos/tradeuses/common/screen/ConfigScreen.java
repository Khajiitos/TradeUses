package me.khajiitos.tradeuses.common.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import joptsimple.internal.Strings;
import me.khajiitos.tradeuses.common.config.Config;
import me.khajiitos.tradeuses.common.screen.widget.EditBoxParagraphAllowed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ConfigScreen extends Screen {
    private EditBoxParagraphAllowed editBox;
    private Button copyColorCodeButton;
    private final Screen parent;
    private final String originalValue;
    private int ticksUntilButtonRenamed;

    public ConfigScreen(Screen parent) {
        super(new TextComponent("Trade Uses Config"));
        this.parent = parent;
        this.originalValue = Strings.join(Config.lines, "\\n");
    }

    public ConfigScreen(Minecraft minecraft, Screen parent) {
        this(parent);
        this.minecraft = minecraft;
    }

    @Override
    protected void init() {
        String value = this.editBox != null ? this.editBox.getValue() : originalValue;

        this.editBox = this.addRenderableWidget(new EditBoxParagraphAllowed(
                Minecraft.getInstance().font,
                this.width / 2 - 100,
                this.height / 2 - 10,
                200,
                20,
                new TextComponent("Tooltip text"))
        );
        this.editBox.setMaxLength(256);
        this.editBox.setValue(value);

        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (button) -> this.onClose()));
        this.copyColorCodeButton = this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 2 + 15, 150, 20, new TextComponent("Copy color code character"), (button) -> {
            if (this.minecraft != null) {
                this.minecraft.keyboardHandler.setClipboard("§");
                button.setMessage(new TextComponent("Copied!"));
                this.ticksUntilButtonRenamed = 30;
            }
        }));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);

        this.font.draw(poseStack, "Placeholders: {uses}, {max_uses}, {uses_left}", 3, 3, 0xFF888888);

        List<Component> preview = new ArrayList<>();

        for (String line : this.editBox.getValue().split("\\\\n")) {
            preview.add(new TextComponent(line
                    .replace("{uses}", "0")
                    .replace("{max_uses}", "12")
                    .replace("{uses_left}", "12")
            ));
        }

        int tooltipWidth = preview.stream().map(this.font::width).max(Comparator.comparingInt(value -> value)).orElse(0) + 8;

        int tooltipHeight = preview.size() * 10 + 10 + (preview.size() >= 2 ? 2 : 0);

        this.font.draw(poseStack, "Preview", this.width / 2.f, this.height / 2.f - tooltipHeight - 32, 0xFFFFFFFF);
        this.renderTooltip(poseStack, preview, Optional.empty(), (this.width - tooltipWidth) / 2 - 8, this.height / 2 - tooltipHeight - 5);
    }

    @Override
    public void tick() {
        if (this.ticksUntilButtonRenamed > 0 && --this.ticksUntilButtonRenamed == 0) {
            this.copyColorCodeButton.setMessage(new TextComponent("Copy color code character"));
        }
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(parent);
        }

        if (!this.originalValue.equals(this.editBox.getValue())) {
            Config.lines.clear();
            Config.lines.addAll(Arrays.asList(this.editBox.getValue().split("\\\\n")));
            Config.save();
        }
    }
}
