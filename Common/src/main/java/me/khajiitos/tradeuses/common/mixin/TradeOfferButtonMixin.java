package me.khajiitos.tradeuses.common.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.khajiitos.tradeuses.common.config.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(targets = {"net.minecraft.client.gui.screens.inventory.MerchantScreen$TradeOfferButton"})
public abstract class TradeOfferButtonMixin extends Button {
    @Final
    @Shadow(aliases = "field_19166")
    MerchantScreen this$0;

    @Final
    @Shadow
    int index;

    protected TradeOfferButtonMixin(int $$0, int $$1, int $$2, int $$3, Component $$4, OnPress $$5) {
        super($$0, $$1, $$2, $$3, $$4, $$5);
    }

    @Inject(at = @At("HEAD"), method = "renderToolTip", cancellable = true)
    public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, CallbackInfo ci) {
        int scrollOff = ((MerchantScreenAccessor)this$0).getScrollOff();
        MerchantMenu menu = this$0.getMenu();
        MerchantOffer offer = menu.getOffers().get(this.index + scrollOff);

        List<Component> tooltipList = new ArrayList<>();
        Optional<TooltipComponent> tooltipImage = Optional.empty();

        if (this.isHovered && menu.getOffers().size() > this.index + scrollOff) {
            if (mouseX < this.x + 20) {
                ItemStack itemStack = offer.getCostA();
                tooltipList.addAll(this$0.getTooltipFromItem(itemStack));
                tooltipImage = itemStack.getTooltipImage();
            } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
                ItemStack itemStack = offer.getCostB();
                if (!itemStack.isEmpty()) {
                    tooltipList.addAll(this$0.getTooltipFromItem(itemStack));
                    tooltipImage = itemStack.getTooltipImage();
                }
            } else if (mouseX > this.x + 65) {
                ItemStack itemStack = offer.getResult();
                tooltipList.addAll(this$0.getTooltipFromItem(itemStack));
                tooltipImage = itemStack.getTooltipImage();
            }

            if (!tooltipList.isEmpty()) {
                tooltipList.add(Component.empty());
            }

            Config.lines.forEach(line -> tooltipList.add(Component.literal(
                    line.replace("{uses_left}", String.valueOf(Math.max(0, offer.getMaxUses() - offer.getUses())))
                            .replace("{uses}", String.valueOf(offer.getUses()))
                            .replace("{max_uses}", String.valueOf(offer.getMaxUses()))
            )));

            this$0.renderTooltip(poseStack, tooltipList, tooltipImage, mouseX, mouseY);
        }
        
        ci.cancel();
    }
}
