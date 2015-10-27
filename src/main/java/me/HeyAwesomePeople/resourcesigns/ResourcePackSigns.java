package me.HeyAwesomePeople.resourcesigns;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ResourcePackSigns extends JavaPlugin implements Listener {

    private File fileconfig = new File(this.getDataFolder() + File.separator + "config.yml");

    @Override
    public void onEnable() {
        if (!fileconfig.exists()) {
            this.saveDefaultConfig();
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        this.reloadConfig();
    }

    public String getResource() {
        if (getConfig().getString("resourcePackLink") == null) return null;
        if (getConfig().getString("resourcePackLink").equals("none")) return null;
        return getConfig().getString("resourcePackLink");
    }

    @EventHandler
    public void signUpdateEvent(SignChangeEvent e) {
        if (e.getLine(0) == null) return;
        if (e.getLine(0).equalsIgnoreCase("%rpsign%")) {
            e.setLine(0, ChatColor.translateAlternateColorCodes('&', getConfig().getString("text")));
        }
    }

    @EventHandler
    public void signInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() == null) return;
        if (e.getClickedBlock().getState() == null) return;
        if (e.getClickedBlock().getType().equals(Material.SIGN) || e.getClickedBlock().getType().equals(Material.SIGN_POST) || e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
            Sign s = (Sign) e.getClickedBlock().getState();

            if (s.getLine(0) == null) return;
            if (ChatColor.stripColor(s.getLine(0)).equals(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getConfig().getString("text"))))) {
                Bukkit.broadcastMessage("7");
                e.getPlayer().setResourcePack(this.getResource());
            }
        }
    }


}

