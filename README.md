# Overview

JSON/q codebase and examples

## Project Structure

The main implementation can be found in `java/`. This contains JSON/q canonical interfaces as well
as reference implementations of the virtual DB features.

### JavaScript

We are leveraging GWT to act as a bridge between Java and JavaScript. The JavaScript-specific code
can be found in `gwt/`. This includes any shims required to bridge into a nice JavaScript API as 
well as storage driver implementations.

## Building

JSON/q is utilizing the gradle build system. To build, simply run `gradle build` from the project 
root. The GWT project is utilizing ant underneath gradle, so you can still build these targets using
ant.

### Prerequisites

 - Oracle Java JDK 1.7
 - Gradle

## Examples

Examples of JSON/q messages can be found in the `examples/` directory. These messages are the
reference for all JSON/q operations.
