package de.sivery.speedyboats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SpeedyBoats extends JavaPlugin implements Listener {
    public void onEnable() {
        System.out.println("[SpeedyBoats] Loading plugin...");

        this.getServer().getPluginManager().registerEvents(this, this);
        //Add crafting recipe
        ItemStack item = new ItemStack(Material.SUGAR);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "Engine Level 1");
        item.setItemMeta(meta);

        // create a NamespacedKey
        NamespacedKey key = new NamespacedKey(this, "engine_lvl1");


        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" R ", "ISI", " R ");

        /* R = REDSTONE
           I = IRON INGOT
           S = SUGAR       */
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('S', Material.SUGAR);

        Bukkit.addRecipe(recipe);
    }

    @EventHandler
    public void onVehicleDrive(VehicleMoveEvent event) {
        Vehicle vehicle = event.getVehicle();
        long len = event.getVehicle().getPassengers().size();
        if (len == 0) {
            return;
        }
        Entity passenger = event.getVehicle().getPassengers().get(0);
        System.out.println(passenger);

        if (vehicle instanceof Boat boat && passenger instanceof Player player) {
            if (player.getInventory().getItemInMainHand().getItemMeta() == null) {
                return;
            }
            Material itemType = player.getInventory().getItemInMainHand().getType();
            String itemName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();

            if (itemType == Material.SUGAR && itemName.equals(ChatColor.RED + "Engine Level 1")){
                boat.setVelocity(new Vector(boat.getLocation().getDirection().getX(), 0.0, boat.getLocation().getDirection().getZ()));
            }
        }
    }
}
