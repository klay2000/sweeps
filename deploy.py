#!/usr/bin/env python

import argparse
import docker
import os

client = docker.from_env()
llApiClient = docker.APIClient(base_url='unix://var/run/docker.sock')
host_images = [ n for n in client.images.list(all=True) if not(n.tags == [])]
host_containers = [ n for n in client.containers.list(all=True) ]


def init_mongo(ip='172.17.0.2'):
    # container = llApiClient.create_container(
    #     'mongo',
    #     ports=[27017],
    #     detach=True,
    #     hostname="mongo",
    #     name="mongodb",
    #     networking_config=llApiClient.create_networking_config({
    #         'dblan': llApiClient.create_endpoint_config(ipv4_address='172.17.0.2',
    #                                                     aliases=['mongo']),
    #     })
    # )
    print("initializing mongo")
    container = client.containers.run("mongo",
                                      hostname="mongodb",
                                      network="dblan",
                                      name="mongodb",
                                      detach=True)
    return container

# make sure host has all necessary containers
def check_dependencies(deps):
        for i in deps:
            if not(i in [ i.tags[0] for i in host_images ]):
                print("pulling image: ", i)
                client.images.pull(i)


def clean_containers():
    print("cleaning containers")
    for c in host_containers:
        c.remove(force=True)


def clean_images():
    print("cleaning images")
    for i in host_images:
        client.images.remove(i.id, force=True)


def main():
    dependencies = ["mongo:latest", "openjdk:latest"]
    check_dependencies(dependencies)
    if not("progmmo:latest" in host_images):
        print("building progmmo container")
        client.images.build(path=".", tag="progmmo:latest")
        print("container built successfully")

    # docker will create duplicate networks without protest so this check needs to be here
    if not("dblan" in [n.name for n in client.networks.list() ]):
        print("creating network")
        client.networks.create("dblan", driver="bridge", internal=True)

    # assemble everything into networks
    dblanar = list(filter(lambda n: n.name == "dblan", client.networks.list()))
    dblan = dblanar[0]

    mongo = init_mongo() if not("mongo:latest" in host_containers) else client.containers.get("mongodb")
    print([i.name for i in client.containers.list() if i.name != None])
    progmmo = client.containers.run("progmmo",
                                    network=dblan.id,
                                    ports={'80/tcp':8080},
                                    name="progmmo",
                                    detach=True)
    print("launching container")
    for l in progmmo.logs():
        print(l)

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--images', help="clean host images before running", action="store_true")
    parser.add_argument('--containers', help="clean host containers before running", action="store_true")
    args = parser.parse_args()

    if args.containers:
        clean_containers()

    if args.images:
        clean_images()
    main()
