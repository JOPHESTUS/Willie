package com.drtshock.willie.command.admin;

import com.drtshock.willie.Willie;
import com.drtshock.willie.command.CommandHandler;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class CommandsCommandHandler implements CommandHandler {
    
    @Override
    public void handle(Willie bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            channel.sendMessage("Set a command with .setcmd <cmd> <output>");
        } else if (args.length == 1) {
            bot.getCommands().delCommand(args[0]);
            channel.sendMessage("Nulled the command \"" + args[0] + "\"");
        } else {
            String command = args[0];
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                if (arg == null ? args[0] != null : !arg.equals(args[0])) {
                    sb.append(arg).append(" ");
                }
            }
            String output = sb.toString().trim();
            bot.getCommands().setCommand(command, output);
            bot.getCommands().save("commands.yml");
            channel.sendMessage(bot.getConfig().getCommandPrefix() + command + " " + output);
        }
    }
}
