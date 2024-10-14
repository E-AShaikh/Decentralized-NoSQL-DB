# Decentralized Cluster-Based NoSQL DB System

The Decentralized Cluster-Based NoSQL DB system is a scalable, document-based NoSQL database designed for distributed environments. It is ideal for applications that require robust and distributed data management, offering a solution for handling large volumes of unstructured data efficiently.

## Architecture

The system is composed of two primary types of nodes:

### Bootstrapping Node
- **Responsibility**: This node is responsible for the initial setup of the system, handling cluster initialization, and user session management.
- **Role**: It plays a crucial role during the system startup and when new users join the system.

### Database Nodes
- **Responsibility**: These nodes are responsible for the core database operations. They handle CRUD (Create, Read, Update, Delete) operations and ensure data consistency and integrity.
- **Operation**: Each node operates independently but is part of the overall cluster, allowing the system to scale horizontally as needed.

## NoSQL-Connector
Additionally, the system uses the **NoSQL-Connector**, a custom-built dependency that features an Object-Relational Mapping (ORM) to facilitate easy interaction between the user's application and the database.
