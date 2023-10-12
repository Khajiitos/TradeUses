package me.khajiitos.tradeuses.common.mixin;

import joptsimple.internal.Strings;
import me.khajiitos.tradeuses.common.config.Config;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {

    @Inject(at = @At("TAIL"), method = "onResourceManagerReload")
    public void onLanguageReload(ResourceManager resourceManager, CallbackInfo ci) {
        if (Strings.join(Config.lines.toArray(new String[0]), "\n").equals(I18n.get("tradeuses.default_tooltip"))) {
            Config.lines.clear();
            Config.save();
        }
    }
}
