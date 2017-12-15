package com.onarandombox.multiverseinventories.command;

import com.dumptruckman.minecraft.util.Logging;
import com.onarandombox.multiverseinventories.MultiverseInventories;
import com.onarandombox.multiverseinventories.WorldGroup;
import com.onarandombox.multiverseinventories.locale.Message;
import com.onarandombox.multiverseinventories.profile.container.ProfileContainer;
import com.onarandombox.multiverseinventories.util.Perm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * The /mvi info Command.
 */
public class MigrateCommand extends InventoriesCommand {

    public MigrateCommand(MultiverseInventories plugin) {
        super(plugin);
        this.setName("Migrate player data from one name to another");
        this.setCommandUsage("/mvinv migrate " + ChatColor.GREEN + "{OLDNAME} {NEWNAME} [saveold]");
        this.setArgRange(2, 3);
        this.addKey("mvinv migrate");
        this.addKey("mvinvmigrate");
        this.setPermission(Perm.COMMAND_INFO.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        String oldName = args.get(0);
        String newName = args.get(1);
        boolean deleteOld = true;
        if (args.size() > 2) {
            if (args.get(2).equalsIgnoreCase("saveold")) {
                deleteOld = false;
            }
        }
        try {
            plugin.getData().migratePlayerData(oldName, newName, Bukkit.getOfflinePlayer(newName).getUniqueId(), deleteOld);
            messager.good(Message.MIGRATE_SUCCESSFUL, sender, oldName, newName);
        } catch (IOException e) {
            Logging.severe("Could not migrate data from name " + oldName + " to " + newName);
            e.printStackTrace();
            messager.bad(Message.MIGRATE_FAILED, sender, oldName, newName);
        }
    }
}

