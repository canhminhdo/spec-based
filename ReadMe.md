# SPECIFICATION-BASED TESTING ENVIRONMENT
SBT is an environment to testing concurrent programs based on specifications.

## Using this environment
0. We have 2 modes, one is local, second one is remote. You may configure in config.CaseStudy

1. Start with your CaseStudy by extending from config.CaseStudy class

2. Configure the application with your own case study in server.ApplicationConfiguration

3. Configure database connection as well as redis, please refer to database.ConenectionFactory, database.RedisClient respectively

4. Overwrite the initial message sending to the system for kick off, getInitialMessage method in your CaseStudy

 
