# Json to Json Transformation 

**Jolt** (JsOn Language for Transform) is a transformation library, written in Java that allows a developer to convert one JSON structure to another. Jolt provides a set of transformation types, each with their own DSL (called specifications), that define the new structure for outgoing JSON data.

# Why Jolt?

JSON is a language independent data format, commonly used for client-server communication especially when it comes to web-services, configuration-files, other data-descriptors, etc. The incoming JSON data simply needs to be formatted or re-labeled for use in another system or repository, such as Hive, HBase, MongoDB, Elasticsearch and Apache Kafka.

Before Jolt, input JSON had to go through various conversions before getting the JSON output.

**Input JSON -> XML -> XSLT/STX -> XML -> JSON Output.**

After Jolt, only the Jolt specification file (in JSON format) needs to be written and applied to input JSON to get the JSON output.

**Input JSON -> spec.json -> JSON Output.**

Also with Jolt, one can gain below advantages:

1. It minimizes Java code changes as most of the work is done using transformations.
2. Any complex transformation logic which can’t be expressed in standard terms can be plugged in via a Java extension class with Jolt.
3. It avoids having to call hundreds of getters and setters to move from one object model to another, which makes moving from one service’s API to an internal model or an internal model to an external format much easier.

**Jolt Transformations:**
Jolt allows the below transformations to generate the desired result:

1. [x] **shift**       : copies data from input to the output tree
2. [x] **default**     : applies default values to the tree.
3. [x] **remove**      : removes data from the tree.
4. [x] **sort**        : sort the Map key values alphabetically.
5. [x] **cardinality** : adjusts the cardinality of input data.

Example using Shift operation:

**Input JSON:**

    {
    "client": {
    "name": "Acme Corp.",
    "address": {
    "street": "123 Main St",
    "state": "CA"
    },
    "employeeDetails": {
    "name": "John Doe",
    "designation": "Manager",
    "phone": "555-1234",
    "home": "555-5678",
    "address": "456 Elm St"
    }
    }
    }

**Jolt Spec:**

    [
    {
    "operation": "shift",
    "spec": {
    "client": {
    "name": "companyName",
    "address": {
    "street": "clientStreetAddress",
    "state": "clientAddressState"
    },
    "employeeDetails": {
    "name": "employee.name",
    "designation": "employee.designation",
    "phone": "employee.contact.phone",
    "home": "employee.contact.home",
    "address": "employee.contact.address"
    }
    }
    }
    },
    {
    "operation": "modify-overwrite-beta",
    "spec": {
    "address": "=concat(@(1,clientStreetAddress),' ',@(1,clientAddressState))"
    }
    },
    {
    "operation": "remove",
    "spec": {
    "clientStreetAddress": "",
    "clientAddressState": ""
    }
    }
    ]

**Output JSON:**

    {
    "companyName": "Acme Corp.",
    "employee": {
    "name": "John Doe",
    "designation": "Manager",
    "contact": {
    "phone": "555-1234",
    "home": "555-5678",
    "address": "456 Elm St"
    }
    },
    "address": "123 Main St CA"
    }

#### **Resources**

JSON to JSON transformation library written in Java. [https://github.com/bazaarvoice/jolt](https://jolt-demo.appspot.com/#inception)

Jolt Transform Demo [https://jolt-demo.appspot.com/#inception](https://jolt-demo.appspot.com/#inception)