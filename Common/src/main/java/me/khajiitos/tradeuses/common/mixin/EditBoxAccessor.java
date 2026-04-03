package me.khajiitos.tradeuses.common.mixin;

import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EditBox.class)
public interface EditBoxAccessor {
    @Accessor
    int getCursorPos();

    @Accessor
    int getHighlightPos();

    @Accessor
    int getMaxLength();

    @Accessor
    String getValue();

    @Invoker
    void callSetValue(String value);

    @Invoker
    void callOnValueChange(String $$0);
}
