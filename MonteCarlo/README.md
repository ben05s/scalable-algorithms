# Monte Carlo - Backend

## Prerequisites

Requires project `common` to be installed.
Run `npm run build-common`.

## App Engine

### Information

Servicename: monte-carlo

### Deploy

Run `npm run deploy`

This will automatically execute `npm run build-common` before deploying.

## Monte Carlo Simulation
Monte Carlo Simulation of a Chess League
Output: Most Likely Team to be Promoted / Relegated after N Simulation Iterations
### Number of Iterations
Number of times the results of a Season will be Simulated
### Selected Chess League
Provide a PGN File of the desired League and Season to be Simulated
### Use ELO Rating System
Choose wheter or not to use the ELO Rating System to predict the outcome of a Game.
If it is not choosen prediction will be random.

## Database

`MCTask`
Saved Monte Carlo Simulations
`MCTaskIteration`
Results from single Iterations of a Monte Carlo Simulation
`MCTaskResult`
Aggregated Result from the Monte Carlo Simulation

One `MCTask` has multiple `MCTaskIteration` datastore entries (1-n).
One `MCTask`has one `MCTaskResult`datastore entries (1-1).