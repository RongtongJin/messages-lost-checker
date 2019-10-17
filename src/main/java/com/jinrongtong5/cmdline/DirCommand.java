package com.jinrongtong5.cmdline;

import com.beust.jcommander.Parameter;
import com.jinrongtong5.checker.MutilFileChecker;

public class DirCommand extends BaseCommand {

    @Parameter(names = {"--dir", "-d"}, description = "Directory of inspection", required = true)
    public String dir;

    @Parameter(names = {"--prefix", "-p"}, description = "Prefix for file filtering")
    public String prefix = null;

    @Override
    public void doCommand() {
        MutilFileChecker.mutilFileCheck(dir, prefix);
    }
}
