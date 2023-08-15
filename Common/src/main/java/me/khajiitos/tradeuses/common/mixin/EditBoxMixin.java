package me.khajiitos.tradeuses.common.mixin;

import me.khajiitos.tradeuses.common.screen.widget.EditBoxParagraphAllowed;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EditBox.class)
public class EditBoxMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;isAllowedChatCharacter(C)Z"), method = "charTyped")
    public boolean isAllowedCharacter(char c) {
        EditBox editBox = (EditBox)(Object)this;

        if (editBox instanceof EditBoxParagraphAllowed && c == '§') {
            return true;
        }

        return SharedConstants.isAllowedChatCharacter(c);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;filterText(Ljava/lang/String;)Ljava/lang/String;"), method = "insertText")
    public String filterText(String str) {
        EditBox editBox = (EditBox)(Object)this;

        if (editBox instanceof EditBoxParagraphAllowed) {
            StringBuilder stringBuilder = new StringBuilder();

            for(char c : str.toCharArray()) {
                if (c == '§' || SharedConstants.isAllowedChatCharacter(c)) {
                    stringBuilder.append(c);
                }
            }

            return stringBuilder.toString();
        }

        return SharedConstants.filterText(str);
    }
}
