# Spec-based testing
**Spec-based** is an environment to testing concurrent Java programs based on specifications by using JPF and Maude.

Brevity, for a formal specification S and a concurrent program P, state sequences are generated from P and checked to be accepted by S. We suppose that S is specified in Maude and P is implemented in Java. Java Pathfinder (JPF) and Maude are then used to generate state sequences from P and to check if such state sequences are accepted by S, respectively. Even without checking any property violations with JPF, JPF often encounters the notorious state space explosion while only generating state sequences. Thus, we propose a technique to generate state sequences from P and check if such state sequences are accepted by S in a stratified way. Some experiments demonstrate that the proposed technique mitigates the state space explosion instances from which otherwise only one JPF instance cannot suffice.

## Features
- Generating all possible state sequences from concurrent Java programs by JPF

- Checking state sequences with Maude specification on the fly.

- Divide & Conquer approach to generating state sequences in a stratified way.

- Parallelizing the whole process of the environment from generating state sequences to checking such state sequences to be accepted by specifications.

## Dependences
- MySQL is a relational database that is used to save all state sequences after being checked by Maude programs.
- Redis is used as a memory cache to avoid duplicate states as well as state sequences.
- RabbitMQ is used as a message broker to deliver messages to workers.
- Maude is used to load specification and test state sequences by using meta-programing supported by itself.
- JPF is known as a model checker software specialized in Java programs that is used to generate state sequences.

## How to install
- Clone the source code to your computer and go to the source code directory.

- Copy **jpf.properties.sample** file to **jpf.properties**. All configuration is set into this file.

- The environment supports two modes running, including local and remote modes.

- Check the configuration with Redis, RabbitMQ as well as MySQL in the configuration file.

- To run experiments, you need to change the "caseStudy" key in the configuration file that corresponds to a case study being checked.

- To extend more case studies, if you want to conduct a new case study with the environment, you need to create your own CaseStudy by extending from config.CaseStudy class. And then do as follows:

    - Configure the application with your own case study in server.ApplicationConfiguration file.

    - Overwrite the initial message sending to the system for kick-off, getInitialMessage method in your own CaseStudy file.
    
    - Remember to change the case study in the configuration file, in which you may run your own case study.

## Case Studies
Some case studies are conducted to demonstrate the correctness as well as the efficiency of the environment as follows.
- Test & Set Protocol (TAS)
- Alternative Bit Protocol (ABP)
- Needham-Schroeder Public-key Authentication Protocol (NSPK and NSLPK)
- Simplified Cloud Synchronization Protocol (CloudSync)

## Publication
- [Specification-based Testing with Simulation Relations](http://ksiresearchorg.ipage.com/seke/seke19paper/seke19paper_27.pdf)

- [A Divide & Conquer Approach to Testing Concurrent Java Programs with JPF and Maude](https://link.springer.com/chapter/10.1007/978-3-030-41418-4_4)

## Known Issues

Checking the known issues at [here](https://github.com/minhcanh99/spec-based/issues). You are appreciated to report bugs to improve the extension as well.

## Release Notes

### 1.0.1
