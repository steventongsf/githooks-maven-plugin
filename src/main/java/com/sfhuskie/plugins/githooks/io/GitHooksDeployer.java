package com.sfhuskie.plugins.githooks.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sfhuskie.plugins.githooks.MojoSettings;

public class GitHooksDeployer {
    MojoSettings settings;
    public GitHooksDeployer(String rootDir) {
        this.settings = new MojoSettings(rootDir);
    }
    

    public void deploy() throws IOException {
        // make sure target directory is valid
        if (!settings.getHooksDir().exists()) {
            throw new IOException(settings.getHooksDir().getAbsolutePath()+" doesn't exist.");
        }
        // if not valid, search parents for .git directory
        // if not found, fail
        // for each file under hooks, see if one exists in target directory
        // if it doesn't exist, copy file
        // if it does exist, check to see if we've merged yet
        // if not merged yet, append to beginning of file. I may just call file instead
        
        // make sure all files in source hooks directory are executable
        FileIO.makeDirectoryFilesExecutable(settings.getHooksSourceDirectory());
        // make sure all files in target hooks directory are executable
        FileIO.makeDirectoryFilesExecutable(settings.getHooksDir());
        
    }

}
