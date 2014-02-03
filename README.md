# H-A-M Craft

Humane Animal Management (Craft)

This is an attempt at an alternative to peaceful mob farming.

The idea is that keeping animals in cages is inhumane, so instead let's deal with them as "items" ;)

This in some ways is a harder approach to mob farming since you need infrastracture, and mobs will not be infinite

The idea is to take Thermal Expansion power, some machines, and treat mobs as items.

## The Plan

### Items:

#### Mob Itemizer


 - captures a mob
 - converts itself into an item for that mob (supports cow/sheep/horse/donkey/pig/squid/chicken)

#### {Animals} (cow/sheep/horse/donkey/pig/squid/chicken)


 - has damage bar
 - once damaged fully will be destroyed

#### {Baby Animals} (cow/sheep/horse/donkey/pig/squid/chicken)

 - has energy bar (i.e. until hatched counter)


### Machines

#### Breeder

 - place 2 adults
 - they take a damage tick
 - produce a child, fully damaged
 - takes energy
 - takes time

#### Incubator

 - place child, it gets "recharged" into an adult

#### Harvester

 - place adult (cow/sheep)
 - get their colleable product
 - cause a damage tick
 - has cooldown
 - takes energy
 - takes time

#### Slaughter House

 - place adult
 - get their loot
 - destroys adult
 - takes energy
 - takes time


#### Special Stuff

- Machines run on RF
- Support pipes (and TE’s auto output system)


## Dev Pre-Requisites

 * JDK, Ideally Java 7
 * Scala

## Buidling

`$ gradle setupDevWorkspace build`

## License

BSD

