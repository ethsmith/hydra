package org.dragonetmc.hydra.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.dragonetmc.hydra.GameManager;
import org.dragonetmc.hydra.objective.KillObjective;
import org.dragonetmc.hydra.objective.Objective;

public class PlayerKillEvent implements Listener {

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        KillObjective killObjective = null;

        for (Objective objective : GameManager.getObjectives()) {
            if (objective instanceof KillObjective) {
                killObjective = (KillObjective) objective;
            }
        }

        if (killObjective == null)
            return;

        Player killer = e.getEntity().getKiller();
        LivingEntity entity = e.getEntity();
        EventCriteria criteria = killObjective.getCriteria();

        if (entity instanceof Player) {
            if (criteria.equals(EventCriteria.PLAYERS) || criteria.equals(EventCriteria.MOBS_AND_PLAYERS))
                killObjective.setKills(killer, killObjective.getKills(killer) + 1);
        } else if (entity instanceof Monster) {
            if (criteria.equals(EventCriteria.MOBS) || criteria.equals(EventCriteria.MOBS_AND_PLAYERS))
                killObjective.setKills(killer, killObjective.getKills(killer) + 1);
        }
    }
}
