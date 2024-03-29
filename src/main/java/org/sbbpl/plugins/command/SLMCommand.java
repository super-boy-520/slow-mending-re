package org.sbbpl.plugins.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.sbbpl.plugins.Slow_mending_re;
import org.sbbpl.plugins.command.commands.*;

public class SLMCommand implements CommandExecutor {

    Slow_mending_re SLM = Slow_mending_re.getSLM();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //检查是否有权限
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("slowmending.command")){
                commandSender.sendMessage("§c你没有权限！");
                return false;
            }
        }

        if (args.length == 0){
            if (commandSender instanceof Player) {
                com_help.playerHelp((Player) commandSender);
            } else {
                com_help.consHelp();
            }
            return true;
        }

        //开始命令判断
        switch (args[0]){
            case ("reload") ->{
                if (commandSender instanceof Player){
                    com_reload.playerReload((Player) commandSender);
                } else if (commandSender instanceof ConsoleCommandSender) {
                    com_reload.consReload();
                }
                return true;
            }
            case ("version") ->{
                if (commandSender instanceof Player){
                    com_version.playerVersion((Player) commandSender);
                } else {
                    com_version.consVersion();
                }
                return true;
            }
            case ("set") ->{
                //检测主副手按长度
                boolean hand;
                //没变量
                System.out.println(args.length);
                if (args.length == 3) hand = true;
                //有变量
                else if (args.length == 4) {
                    //如果是主手
                    if (args[3].equals("main")) {
                        hand = true;
                    }
                    //如果是副手
                    else if (args[3].equals("off")) {
                        hand = false;
                    }
                    //都不是
                    else {
                        if (commandSender instanceof Player) {
                            commandSender.sendMessage("§c命令参数错误！");
                        } else {
                            SLM.getLogger().info("命令参数错误!");
                        }
                        return true;
                    }
                }else {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("§c命令长度错误！");
                    } else {
                        SLM.getLogger().info("命令长度错误!");
                    }
                    return true;
                }

                //读取玩家
                Player targetPlayer;
                try {
                    targetPlayer = SLM.getServer().getPlayer(args[1]);
                    if (targetPlayer == null){
                        if (commandSender instanceof Player) {
                            commandSender.sendMessage("§c玩家未找到！");
                        } else {
                            SLM.getLogger().info("玩家未找到!");
                        }
                        return true;
                    }
                }catch (Exception e){
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("§c玩家未找到！");
                    } else {
                        SLM.getLogger().info("玩家未找到!");
                    }
                    return true;
                }

                //读取次数
                int num;
                try {
                    num = Integer.parseInt(args[2]);
                }catch (Exception e){
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("§c次数错误！");
                    } else {
                        SLM.getLogger().info("次数错误!");
                    }
                    return true;
                }

                //调用
                if (commandSender instanceof Player) {
                    com_set.playerSet(targetPlayer,num,(Player)commandSender,hand);
                } else {
                    com_set.consSet(targetPlayer,num,hand);
                }
                return true;
            }
            case ("add") ->{
                //检测主副手按长度
                boolean hand;
                //没变量
                if (args.length == 3) hand = true;
                //有变量
                else if (args.length == 4) {
                    //如果是主手
                    if (args[3].equals("main")) {
                        hand = true;
                    }
                    //如果是副手
                    else if (args[3].equals("off")) {
                        hand = false;
                    }
                    //都不是
                    else {
                        if (commandSender instanceof Player) {
                            commandSender.sendMessage("§c命令参数错误！");
                        } else {
                            SLM.getLogger().info("命令参数错误!");
                        }
                        return true;
                    }
                }else {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("§c命令长度错误！");
                    } else {
                        SLM.getLogger().info("命令长度错误!");
                    }
                    return true;
                }

                //读取玩家
                Player targetPlayer = null;
                try {
                    targetPlayer = SLM.getServer().getPlayer(args[1]);
                    if (targetPlayer == null){
                        if (commandSender instanceof Player) {
                            commandSender.sendMessage("§c玩家未找到！");
                        } else {
                            SLM.getLogger().info("玩家未找到!");
                        }
                        return true;
                    }
                }catch (Exception e){
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("§c玩家未找到！");
                    } else {
                        SLM.getLogger().info("玩家未找到!");
                    }
                }

                //读取次数
                int num;
                try {
                    num = Integer.parseInt(args[2]);
                }catch (Exception e){
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage("§c次数错误！");
                    } else {
                        SLM.getLogger().info("次数错误!");
                    }
                    return true;
                }

                //调用
                if (commandSender instanceof Player) {
                    com_add.playerAdd(targetPlayer,num,(Player)commandSender,hand);
                } else {
                    com_add.consAdd(targetPlayer,num,hand);
                }
                return true;
            }
            //give
            case ("givecard") ->{
                //检测sender
                if (!(commandSender instanceof Player)){
                    SLM.getLogger().info("不能为非玩家执行！");
                    return true;
                }

                //检测长度
                if (args.length != 5){
                    commandSender.sendMessage("§c命令长度错误！");
                    return true;
                }

                //检测主手
                if (((Player) commandSender).getEquipment().getItemInMainHand().getType() != Material.AIR){
                    commandSender.sendMessage("§c请将移除主手物品。");
                    return true;
                }
                //获取玩家并执行
                try{
                    Player player = Bukkit.getPlayer(args[1]);
                    int num = Integer.parseInt(args[2]);
                    int frequency = Integer.parseInt(args[3]);
                    boolean isset;
                    if (args[4].equals("set")) {
                        isset = true;
                    } else if (args[4].equals("add")) {
                        isset = false;
                    } else {
                        commandSender.sendMessage("§c模式参数错误！");
                        return true;
                    }
                    com_give.give(player, frequency, isset, num);
                    commandSender.sendMessage("§b成功给予物品。");
                    return true;
                }catch (Exception e){
                    commandSender.sendMessage("§c参数错误！");
                    return true;
                }

            }
            default -> {
                if (commandSender instanceof Player) {
                    com_help.playerHelp((Player) commandSender);
                } else {
                    com_help.consHelp();
                }
                return true;
            }
        }
    }
}
