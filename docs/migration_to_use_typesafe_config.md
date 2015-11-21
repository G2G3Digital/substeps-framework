Properties migration from Substeps 1.1.x to 2.x
===============================================

Why?
----
It started to feel there were too many different places to configure Substeps and they were not very readable.

What have you foisted on me now?
--------------------------------
Typesafe Config - you can read more about the range of options this can bring here: 
[https://github.com/typesafehub/config](https://github.com/typesafehub/config)

Can I continue to use the old property style?
---------------------------------------------
Yes, but at some point in the future this property style it is likely be removed and some new features you may not be 
able to configure fully.

Continue to use *localhost.properties* for the default configuration and *-Denvironment="qa"* to trigger the
usage of *qa.properties* as normal.

These properties will now be picked up from the classpath, cannot remember if this is exactly what happens currently.

Ok so there could have been some fibs about things just working like before, due to the nature of Typesafe Config its
not possible to load property files or add manual properties to the Substeps API *com.technophobia.substeps.model.Configuration*
class, these methods have now been deprecated and no longer have an affect on the properties.

Also any libraries using the old Configuration class no longer get provided nulls when a property is not there, it will
cause an exception of *com.typesafe.config.ConfigException.Missing* to be thrown

How do I get started with the new property style?
-------------------------------------------------
0. Not sure yet, I'm writing the read me before any changes have been made. TODO: remove this line once finished.
0. Enable the new property with one of the following command line properties.
    * -Dconfig.resource="qa-chrome.conf"
    * -Dconfig.file="/home/substeps/properties/qa-chrome.conf"
    * -Dconfig.url="http://test.me/properties/qa-chrome.conf"
