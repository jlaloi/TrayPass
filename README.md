TrayPass
========

Java Tray Automatic Actions Implementation

Allowing you for example:

- Auto form login (Opening a new Internet browser to a specified url, wait for a specific image and then send credentials, etc.)
- Recurring tasks management (Each 5 min, log to a database, get the number of session and save it to a file)
- Managing several passwords (Put in the clipboard a deciphered password after entering the main application password)
- etc.

Usage
-----
Download and extract the archive TrayPass.zip. Make sure you have at least Java 1.6 installed and it is present in the path (or modify the .bat to specify the full path of the binary). 

Double-click the "Tray Pass.bat".

General Principle
-----
In the menu configuration file, each line represents one item in the tray menu.

There is a specific syntax for all available actions described in the menu: Configuration &gt; Syntax help

The format is: {&lt;Label (Optional)&gt;, Path to the icon (Can be any file) (Optional)} (&lt;Action&gt;)+

For example, for one menu entry, the below line will be present:

{Log to my application,c:\MyMenuIcon.png}@var(toSearch,@prompt(What are you looking for?)) @execute(C:\Users\MyUser\AppData\Local\Google\Chrome\Application\Chrome.exe,http://www.google.com) @wait(1000) @send(@concat(@var(toSearch)),{enter})

Or simply:

@clipboard(TrayPass)

Example
-----
Check the examples directory.

Pack Principle
-----
It is possible to create a "pack", it is a ZIP archive file which can regroup actions to be executed and image files.

For example:

In the menu configuration file, the below line is present: 

{Log to my application}@pack(c:\MyPack.zip)

The MyPack.zip contains:
- The file "line.txt" will all actions to be executed,
- Some pictures used by the actions.

Recurring tasks principle
-----
In the menu configuration file, the below entry will be present:

task:{&lt;Task Name&gt;, Path to the icon (Can be any file) (Optional)}&lt;time in second, actions&gt;

For example:

task:{Display time}5, @info(@date())

In the menu configuration &gt; Tasks, tasks can be controlled.

Plugin
-----
In order to create a plugin, create a new JAVA class file extending the class plugin.java, create a jar and copy it to the "plugin" directory.

See the plugin directory for an example.
