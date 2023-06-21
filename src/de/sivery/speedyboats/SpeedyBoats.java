package de.sivery.speedyboats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SpeedyBoats extends JavaPlugin implements Listener {
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats] Loading plugin...");
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats]     Registering event listener...");

        this.getServer().getPluginManager().registerEvents(this, this);

        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats]     Initializing crafting recipes...");

        // Add Level 1 recipes
        ItemStack item = new ItemStack(Material.SUGAR);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.RED + "Engine Level 1");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(Collections.singletonList(ChatColor.RESET.toString() + ChatColor.GRAY +"Hold the engine in your main hand so the engine accelerates you and your boat."));

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
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats]     Loaded Engine level 1 recipe \u2714");

        ItemStack lvl1Item = item;

        // Add Level 2 recipes
        item = new ItemStack(Material.REDSTONE);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.RED + "Engine Level 2");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(Collections.singletonList(ChatColor.RESET.toString() + ChatColor.GRAY +"Hold the engine in your main hand so the engine accelerates you and your boat."));

        item.setItemMeta(meta);

        // create a NamespacedKey
        key = new NamespacedKey(this, "engine_lvl2");

        recipe = new ShapedRecipe(key, item);

        recipe.shape(" D ", "GEG", " D ");

        /* D = DIAMOND
           G = GOLD INGOT
           E = ENGINE LVL1 */
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(lvl1Item));

        Bukkit.addRecipe(recipe);
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats]     Loaded Engine level 2 recipe \u2714");

        ItemStack lvl2Item = item;

        // Add Level 3 recipes
        item = new ItemStack(Material.POPPED_CHORUS_FRUIT);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.RED + "Engine Level 3");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(Collections.singletonList(ChatColor.RESET.toString() + ChatColor.GRAY +"Hold the engine in your main hand so the engine accelerates you and your boat."));

        item.setItemMeta(meta);

        // create a NamespacedKey
        key = new NamespacedKey(this, "engine_lvl3");

        recipe = new ShapedRecipe(key, item);

        recipe.shape(" D ", "GEG", " D ");

        /* N = NETHERITE INGOT
           S = NAUTILUS SHELL
           E = ENGINE LVL2 */
        recipe.setIngredient('D', Material.NETHERITE_INGOT);
        recipe.setIngredient('G', Material.NAUTILUS_SHELL);
        recipe.setIngredient('E', new RecipeChoice.ExactChoice(lvl2Item));

        Bukkit.addRecipe(recipe);
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats]     Loaded Engine level 3 recipe \u2714");
        getServer().getConsoleSender().sendMessage(ChatColor.BLUE+"[SpeedyBoats] Successfully loaded! Have fun!");

    }

    @EventHandler
    public void onVehicleDrive(VehicleMoveEvent event) {
        Vehicle vehicle = event.getVehicle();
        long len = event.getVehicle().getPassengers().size();
        if (len == 0) {
            return;
        }
        Entity passenger = event.getVehicle().getPassengers().get(0);

        if (vehicle instanceof Boat boat && passenger instanceof Player player) {
            if (player.getInventory().getItemInMainHand().getItemMeta() == null) {
                return;
            }
            Material itemType = player.getInventory().getItemInMainHand().getType();
            String itemName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();

            if (itemType == Material.SUGAR && itemName.equals(ChatColor.RED + "Engine Level 1")){
                boat.setVelocity(new Vector(boat.getLocation().getDirection().getX(), 0.0, boat.getLocation().getDirection().getZ()));
            }
            if (itemType == Material.REDSTONE && itemName.equals(ChatColor.RED + "Engine Level 2")){
                boat.setVelocity(new Vector(boat.getLocation().getDirection().multiply(2).getX(), 0.0, boat.getLocation().getDirection().getZ()));
            }
            if (itemType == Material.POPPED_CHORUS_FRUIT && itemName.equals(ChatColor.RED + "Engine Level 3")){
                boat.setVelocity(new Vector(boat.getLocation().getDirection().multiply(3).getX(), 0.0, boat.getLocation().getDirection().getZ()));
            }
        }
    }
}
