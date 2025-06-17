from diagrams import Cluster, Diagram
from diagrams.aws.compute import ECS
from diagrams.aws.network import APIGateway
from diagrams.onprem.queue import Rabbitmq
from diagrams.onprem.database import PostgreSQL
from diagrams.onprem.monitoring import Prometheus
from diagrams.onprem.compute import Server
from diagrams.programming.framework import Spring
from diagrams.onprem.client import Client
from diagrams.gcp.operations import Monitoring

with Diagram("Microservices Architecture", show=True):

    client = Client("User Client")
    config = Server("Config Server")
    registry = Spring("Eureka")

    with Cluster("Microservices"):
        company = ECS("Company Service")
        job = ECS("Job Service")
        review = ECS("Review Service")

    gateway = APIGateway("API Gateway")

    db = PostgreSQL("PostgreSQL")
    mq = Rabbitmq("RabbitMQ")
    zipkin = Monitoring("Zipkin Tracing")

    # Client to API Gateway
    client >> gateway

    # Config to services
    [company, job, review, gateway] >> config

    # Gateway routes
    gateway >> [company, job, review]

    # Service registry
    [company, job, review, gateway] >> registry

    # DB connections
    [company, job, review] >> db

    # Messaging
    [company, review] >> mq

    # Tracing
    [company, job, review, gateway] >> zipkin