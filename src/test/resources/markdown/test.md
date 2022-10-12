ins.md


UW PICO 5.09                                                                                           File: plugins.md

# Git-Beaver Plugins #

The table below specifies which plugins will be integrated into the docker image:


| GitUrl             | RepoName                 | SourcePath    | MainClass                          |
|:-------------------|:-------------------------|:--------------|:-----------------------------------|
| https://github.com | git-beaver-file-resolver | src/main/java | org.jkube.gitbeaver.ResolverPlugin |

Note: The plugins "git-beaver-base" and "git-beaver-markdown" should not be included into the table,
because they will be loaded by default (in order to become able to parse and process this markdown file).

Explanation of columns:

* GitUrl: the URL prefix of the git repository (not including the repo name)
* RepoName: the name of the repository (the last element of the repo URL, not including the extension ".git")
* SourcePath: the file path of the folder containing the java source files (using "/" as separator, not including leading or trailing "/")
* MainClass: the class name of the main class (using "." as separator, not including the extension ".java")



