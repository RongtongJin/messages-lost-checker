package com.jinrongtong5.cmdline;

import java.util.Objects;

import com.beust.jcommander.Parameter;
import com.jinrongtong5.checker.DirChecker;

public class DirCommand extends BaseCommand {

    @Parameter(names = {"--dir", "-d"}, description = "Directory of inspection", required = true)
    private String dir = null;

    @Parameter(names = {"--prefix", "-p"}, description = "Prefix for file filtering")
    private String prefix = null;

    @Override
    public void doCommand() {
        Objects.requireNonNull(dir);
        DirChecker.mutilFileCheck(dir, prefix);
    }
}
