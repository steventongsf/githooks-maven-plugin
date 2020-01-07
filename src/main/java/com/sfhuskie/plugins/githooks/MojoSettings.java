package com.sfhuskie.plugins.githooks;

import java.io.File;

public class MojoSettings {
    public static final String default_checkstyle_url = "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.27/checkstyle-8.27-all.jar";
    public static final File userDir = new File(System.getProperty("user.dir"));
    File hooksSourceDirectory = new File(this.userDir.getAbsolutePath()+"/src/main/hooks");
    File hooksDir;
    File rootDir;
    public String hooksRelativeDir = "/hooks";

    public MojoSettings() {
    }
    public File getHooksSourceDirectory() {
        return hooksSourceDirectory;
    }
}
