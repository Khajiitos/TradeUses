package me.khajiitos.tradeuses.common.screen.widget;

import me.khajiitos.tradeuses.common.mixin.EditBoxAccessor;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class EditBoxParagraphAllowed extends EditBox {

    public EditBoxParagraphAllowed(Font font, int x, int y, int w, int h, Component text) {
        super(font, x, y, w, h, text);
    }

    @Override
    public boolean keyPressed(int key, int idk, int idk2) {
        if (key == GLFW.GLFW_KEY_ENTER && this.canConsumeInput()) {
            this.insertText("\\n");
            return true;
        }
        return super.keyPressed(key, idk, idk2);
    }

    private String filterText(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        for(char c : str.toCharArray()) {
            if (c == '§' || StringUtil.isAllowedChatCharacter(c)) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean charTyped(char c, int i) {
        if (!this.canConsumeInput()) {
            return false;
        } else if (c == '§' || StringUtil.isAllowedChatCharacter(c)) {
            this.insertText(Character.toString(c));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void insertText(@NotNull String str) {
        EditBoxAccessor accessor = (EditBoxAccessor) this;
        int selectionStart = Math.min(accessor.getCursorPos(), accessor.getHighlightPos());
        int selectionEnd = Math.max(accessor.getCursorPos(), accessor.getHighlightPos());
        int idk = accessor.getMaxLength() - accessor.getValue().length() - (selectionStart - selectionEnd);
        String filteredText = filterText(str);
        int filteredTextLength = filteredText.length();
        if (idk < filteredTextLength) {
            filteredText = filteredText.substring(0, idk);
            filteredTextLength = idk;
        }

        String newValue = (new StringBuilder(accessor.getValue())).replace(selectionStart, selectionEnd, filteredText).toString();
        if (accessor.getFilter().test(newValue)) {
            accessor.setValue(newValue);
            this.setCursorPosition(selectionStart + filteredTextLength);
            this.setHighlightPos(accessor.getCursorPos());
            accessor.callOnValueChange(accessor.getValue());
        }
    }
}