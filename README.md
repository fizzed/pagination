# Pagination by Fizzed

[![Maven Central](https://img.shields.io/maven-central/v/com.fizzed/pagination?color=blue&style=flat-square)](https://mvnrepository.com/artifact/com.fizzed/pagination)

The following platforms are tested using the [Fizzed, Inc.](http://fizzed.com) build system:

[![Linux x64](https://img.shields.io/badge/Linux%20x64-passing-green)](buildx-results.txt)
[![Linux arm64](https://img.shields.io/badge/Linux%20arm64-passing-green)](buildx-results.txt)
[![Linux armhf](https://img.shields.io/badge/Linux%20armhf-passing-green)](buildx-results.txt)
[![Linux riscv64](https://img.shields.io/badge/Linux%20riscv64-passing-green)](buildx-results.txt)
[![Linux MUSL x64](https://img.shields.io/badge/Linux%20MUSL%20x64-passing-green)](buildx-results.txt)
[![MacOS x64](https://img.shields.io/badge/MacOS%20x64-passing-green)](buildx-results.txt)
[![MacOS arm64](https://img.shields.io/badge/MacOS%20arm64-passing-green)](buildx-results.txt)
[![FreeBSD x64](https://img.shields.io/badge/FreeBSD%20x64-passing-green)](buildx-results.txt)
[![OpenBSD x64](https://img.shields.io/badge/OpenBSD%20x64-passing-green)](buildx-results.txt)

Utility classes for Java 8+ for working with paginated datasets (e.g. querying database, etc.)

## Usage

In your `pom.xml` add the following dependency:

```xml
<dependency>
  <groupId>com.fizzed</groupId>
  <artifactId>pagination-cursor</artifactId>
  <version>VERSION-HERE</version>
</dependency>
```

If you're working w/ Ebean.io ORM add the following:

```xml
<dependency>
  <groupId>com.fizzed</groupId>
  <artifactId>pagination-ebean</artifactId>
  <version>VERSION-HERE</version>
</dependency>
```

## License

Copyright (C) 2025+ Fizzed, Inc.

This work is licensed under the Apache License, Version 2.0. See LICENSE for details.
