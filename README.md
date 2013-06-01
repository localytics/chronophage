Chronophage
===========
A Scala library for consuming all manner of datetime strings and converting them into Joda Time DateTime objects.

Design Intent
===========
Chronophage was crafted over the course of a year or more dealing with a wide variety of poorly formatted datetime strings.

In particular Chronophage is able to decipher 12 hr time in a wide variety of languages and locales and parse the strings appropriately.

Import
===========
**JAR**

Simply download the JAR and use it locally.

**SBT**

Create a project using the code sample below and add it as a dependency to your project using `dependsOn(chronophage)`

```scala
lazy val chronophage = RootProject(uri("git://github.com/localytics/chronophage.git"))
```

Usage
===========
**Simple**

The simplest option is to use the Chronophage object which dispatches to an instance of Chronophage using the default AMs and PMs.

```scala
import com.localytics.Chronophage

Chronophage.parseDateTime("2010-12-12T01:00:00 a.m.-05:00")
```

**Intermediate**

If there are AMs and PMs that Chronophage does not support you can tell Chronophage about then using this syntax. Better yet, open a pull request.

```scala
import com.localytics.Chronophage

val chronophage = new Chronophage(additionalAMStrings = Set(), additionalPMStrings = Set())

chronophage.parseDateTime("2010-12-12T01:00:00 a.m.-05:00")
```

**Advanced**

This is a performance optimization if there are AMs and PMs that Chronophage does not support and are known to occur either at either the start or the end of the time part of the datetime string.
AMs and PMs generally occur at the start of the time part in asian locales and at the end of the time part in all other locales. Of course a pull request would be greatly appreciated.

```scala
import com.localytics.Chronophage

val chronophage = new Chronophage(additionalAMsAtEnd = Set(), additionalPMsAtEnd = Set(),
                                  additionalAMsAtStart = Set(), additionalPMsAtStart = Set())

chronophage.parseDateTime("2010-12-12T01:00:00 a.m.-05:00")
```

Contributors
===========
* [Benjamin Darfler](https://github.com/bdarfler "Benjamin Darfler")