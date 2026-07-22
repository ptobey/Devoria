package me.devoria.integrations.modelengine;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.bone.Nameable;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public final class ModelEngineHealthBar {

    private ModelEngineHealthBar() {
    }

    public static void update(Entity entity, String currentHealth, String maxHealth) {
        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity.getUniqueId());
        if (modeledEntity == null) {
            return;
        }

        for (ActiveModel activeModel : modeledEntity.getModels().values()) {
            Nameable healthBar = activeModel.getNametagHandler().getBones().get("healthbar");
            if (healthBar != null) {
                healthBar.setCustomNameVisible(true);
                healthBar.setCustomName(ChatColor.DARK_RED + "❤ " + currentHealth + "/" + maxHealth);
            }
        }
    }
}
