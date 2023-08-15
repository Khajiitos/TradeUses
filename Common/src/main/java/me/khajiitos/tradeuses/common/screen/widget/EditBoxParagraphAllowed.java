package me.khajiitos.tradeuses.common.screen.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class EditBoxParagraphAllowed extends EditBox {
    /*
    Because EditBox has a lot of private members
    and doesn't like to be extended,
    we're going to allow § characters
    using a Mixin.
    */

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
}