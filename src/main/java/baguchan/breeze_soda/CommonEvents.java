package baguchan.breeze_soda;

import baguchan.breeze_soda.recipe.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = BreezeSoda.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void sodaTip(ItemTooltipEvent event) {
        if (event.getItemStack().get(ModDataComponents.SODA.get()) != null) {
            event.getToolTip().add(Component.translatable("breeze_soda.has_soda"));
        }
    }
}
