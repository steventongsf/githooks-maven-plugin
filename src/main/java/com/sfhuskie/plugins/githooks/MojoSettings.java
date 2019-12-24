package com.sfhuskie.plugins.githooks;

import java.io.File;

public class MojoSettings {
    public static final File userDir = new File(System.getProperty("user.dir"));
    File hooksSourceDirectory = new File(this.userDir.getAbsolutePath()+"/src/main/hooks");
    File hooksDir;
    File rootDir;
    public String hooksRelativeDir = "/.git/hooks";

    public MojoSettings() {
    }
    public File getHooksSourceDirectory() {
        return hooksSourceDirectory;
    }
}
