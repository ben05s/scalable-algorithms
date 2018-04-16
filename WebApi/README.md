# WebApi

## Prerequisites

Requires project `common` to be installed.
Run `npm run build-common`.

## App Engine

### Information

Servicename: web-api

### Deploy

Run `npm run deploy`

This will automatically execute `npm run build-common` before deploying.

## Endpoints

### Monte Carlo Simulation

GET `/mc/task`
Returns all Tasks

GET `/mc/task?id`
Returns one Task

POST `/mc/task/add`
Save Task to Datastore (task will not be started)

GET `/mc/task/delete?id`
Delete Task from all Datastore Tables

GET `/mc/task/enqueue?id`
Starts a Task by pushing Items to the Monte Carlo-Queue

GET `/mc/task/dequeue?id`
Removes all Items from the Monte Carlo-Queue

GET `/mc/task/iterations?id`
Returns all Iterations from the Simulation

GET `/mc/task/result?id`
Returns the aggregated result from the Simulation

GET `/mc/task/progress?id`
Returns the current progress of a Task

POST `/mc/upload/pgn`
Extracts all Teams and Players from a PGN file and returns them

### TSPTask

Displays all TSP tasks.

### TSPTaskIteration

Has a parameter `task` to only display iterations from a specific task.
If no `task` param is specified, all entries are shown.