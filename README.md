# Json to Json Transformation 
# **Introduction**

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

**Features**

**JSON Transformation:** 

1. Utilizes the Jolt library to apply custom transformations to incoming JSON data.
2. **Database Storage:** Saves the transformed JSON data to a relational database using Spring Data JPA.
3. **REST Endpoint:** An endpoint /transform that accepts POST requests with raw JSON bodies.

**Prerequisites:**
1. Java 11 or higher installed
2. Maven 3.x or higher installed
3. Getting Started
4. Clone the repository to your local machine.
5. Navigate to the root directory of the project.
6. Run **mvn clean install** to build the project and download dependencies.
7. Execute **java -jar target/CustomJsonTransformation-0.0.1-SNAPSHOT.jar** to start the application.
8. Configuration
9. Edit the **application.yaml** file in the src/main/resources directory to specify the Jolt specification and database configuration.

**Usage:**

Once the application is running, you can send a POST request to http://localhost:8080/transform with a JSON body to transform the JSON data.

Example curl command:

`curl --header "Content-Type: application/json" \
--request POST \
--data '{"input":"json"}' \`

http://localhost:8080/transform

**Input Json:** `{
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
} `with your actual JSON input payload.

**Jolt Spec:**
`[
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
]`

**Transformation Json output:**
`{
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
}`


**Testing**

To run the tests, execute **mvn test** in the terminal from the project root directory.

**Contributing**

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

**License**

This project is licensed under the Apache License 2.0. See the LICENSE file for details.