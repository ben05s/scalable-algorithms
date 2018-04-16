# Travelling Salesman Problem - Backend

## Prerequisites

Requires project `common` to be installed.
Run `npm run build-common`.

## App Engine

### Information

Servicename: travelling-salesman-problem

### Deploy

Run `npm run deploy`

This will automatically execute `npm run build-common` before deploying.

## TSP

### Selection

### Crossover

### Mutation

## Database

Is currently using `TSPTask` and `TSPTaskIteration`.

One `TSPTask` has multiple `TSPTaskIteration` datastore entries (1-n).
