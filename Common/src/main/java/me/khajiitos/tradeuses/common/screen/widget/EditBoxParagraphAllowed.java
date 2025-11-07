package me.khajiitos.tradeuses.common.screen.widget;

import me.khajiitos.tradeuses.common.mixin.EditBoxAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class EditBoxParagraphAllowed extends EditBox {

    public EditBoxParagraphAllowed(Font font, int x, int y, int w, int h, Component text) {
        super(font, x, y, w, h, text);
    }

    @Override
    public boolean keyPressed(@NotNull KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_ENTER && this.canConsumeInput()) {
            this.insertText("\\n");
            return true;
        }
        return super.keyPressed(event);
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
    public boolean charTyped(@NotNull CharacterEvent event) {
        if (!this.canConsumeInput()) {
            return false;
        } else if (event.codepoint() == '§' || event.isAllowedChatCharacter()) {
            this.insertText(event.codepointAsString());
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
            accessor.callSetValue(newValue);
            this.setCursorPosition(selectionStart + filteredTextLength);
            this.setHighlightPos(accessor.getCursorPos());
            accessor.callOnValueChange(accessor.getValue());
        }
    }
}