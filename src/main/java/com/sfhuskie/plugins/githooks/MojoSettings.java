package com.sfhuskie.plugins.githooks;

import java.io.File;

public class MojoSettings {
    public static final File userDir = new File(System.getProperty("user.dir"));
    File hooksSourceDirectory = new File(this.userDir.getAbsolutePath()+"/src/main/hooks");
    File hooksDir;
    File rootDir;
    public String hooksRelativeDir = "/.git/hooks";

    public MojoSettings(String rootDir) {
        this.rootDir = new File(rootDir);
        this.hooksDir = new File(this.rootDir+this.hooksRelativeDir);
        
    }
    public File getHooksDir() {
        return hooksDir;
    }

    public File getRootDir() {
        return rootDir;
    }


    public File getHooksSourceDirectory() {
        return hooksSourceDirectory;
    }
}
