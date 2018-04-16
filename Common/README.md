# Common

## Notes

This project has to be built/installed in order to use the other java projects.

Run `npm run build`

## Maven Project

* Shared Entities
* Objectify Data Mapper
* Abstract Servlet
* Entity to Json converter

## Objectify Data Mapper

All information about the data mapper can be found here: [objectify wiki](https://github.com/objectify/objectify/wiki)

### How to create a new entity

* Create a new entity in the `clc3.common.entities` package.
* Create an entity dao in the `clc3.common.daos` package.
* Register the entity in the `clc3.common.objectify.OfyListener` class
