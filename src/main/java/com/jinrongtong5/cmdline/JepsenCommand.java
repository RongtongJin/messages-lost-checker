package com.jinrongtong5.cmdline;

import java.io.IOException;

import com.beust.jcommander.Parameter;
import com.jinrongtong5.checker.JepsenDirChecker;
import com.jinrongtong5.checker.JepsenFileChecker;

public class JepsenCommand extends BaseCommand {

    @Parameter(names = {"--file", "-f"}, description = "File of inspection")
    private String file = null;

    @Parameter(names = {"--dir", "-d"}, description = "Directory of inspection")
    private String dir = null;

    @Parameter(names = {"--prefix", "-p"}, description = "Prefix for file filtering")
    private String prefix = null;

    @Override
    public void doCommand() {
        if (file == null && dir == null) {
            System.out.println("Please input a file name or directory name.");
            System.exit(0);
        }

        if (file != null) {
            try {
                JepsenFileChecker.singleFileCheck(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JepsenDirChecker.mutilFileCheck(dir, prefix);
        }
    }
}
