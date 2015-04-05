# Using the Plugin on Linux #

This paragraph was contributed by icdpdc, see [Issue 23](http://code.google.com/p/tarlog-plugins/issues/detail?id=23)

To Configure **Open Shell** and **Open Explorer** commands in the Eclipse-Menu Window > Preferences > Tarlog Plugins:


### Open Shell command ###

```
/usr/bin/nohup gnome-terminal --working-directory={0} & 
```

### Open Explorer Command ###
```
/usr/bin/nohup nautilus   {0} &
```