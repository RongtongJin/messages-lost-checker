package com.jinrongtong5.cmdline;

import java.util.HashMap;
import java.util.Map;

import com.beust.jcommander.JCommander;

public class MainCommand {
    public static void main(String[] args) {
        Map<String, BaseCommand> commands = new HashMap<>();
        commands.put("dir", new MutilFileCommand());
        commands.put("file", new SingleFileCommand());

        JCommander.Builder builder = JCommander.newBuilder();
        JCommander jc = builder.build();
        for (String cmd : commands.keySet()) {
            builder.addCommand(cmd, commands.get(cmd));
        }
        jc.parse(args);

        if (jc.getParsedCommand() == null) {
            jc.usage();
        } else {
            BaseCommand command = commands.get(jc.getParsedCommand());
            if (command != null) {
                command.doCommand();
            } else {
                jc.usage();
            }
        }
    }
}
