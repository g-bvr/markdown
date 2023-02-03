# Repository g-bvr/markdown

This repository defines a plugin that can be used to enhance the built-in set of commands
to allow parsing and deconstructing markdown files.

## Activation

This plugin can be integrated into the [core docker image](https://hub.docker.com/r/gitbeaver/core/tags)
by executing the following beaver script:

```
GIT CLONE https://github.com/g-bvr markdown main
PLUGIN COMPILE markdown/src/main/java
PLUGIN ENABLE org.jkube.gitbeaver.MarkdownPlugin
```

A more convenient way to build a gitbeaver release with multiple
plugins (based on a tabular selection)
is provided by E. Breuninger GmbH & Co. in the public repository
[e-breuninger/git-beaver](https://github.com/e-breuninger/git-beaver).

## Documentation of defined commands

A list of all commands defined by this plugin can be found in this [automatically generated documentation](https://htmlpreview.github.io/?https://raw.githubusercontent.com/g-bvr/markdown/main/doc/MarkdownPlugin.html). 
