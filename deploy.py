#!/usr/bin/env python

import argparse
import docker
import os

client = docker.from_env()
llApiClient = docker.APIClient(base_url='unix://var/run/docker.sock')
host_images = [ n for n in client.images.list(all=True) if not(n.tags == [])]
host_containers = [ n for n in client.containers.list(all=True) ]


def init_mongo(ip='172.17.0.2'):
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

def init_networks():
    # TODO: finish fixing up this networking shit
    net = client.networks.create("dblan",
                                   driver="overlay",
                                   internal=False,
                                   check_duplicate=True
                                   )
    return net

def main():
    check_dependencies(["mongo:latest", "openjdk:latest"])
    if not("progmmo" in host_images):
        print("building progmmo container")
        client.images.build(path=".", tag="progmmo")
        print("container built successfully")

    # assemble everything into networks
    # docker will create duplicate networks without protest so this check needs to be here
    try:
        nets = init_networks()
    except:
        nets = client.networks.list()

    mongo = init_mongo() if not("mongo:latest" in host_containers) else client.containers.get("mongodb")
    progmmo = client.containers.run("progmmo",
                                    network="dblan",
                                    ports={'8080/tcp':8080, '443/tcp':443},
                                    name="progmmo",
                                    detach=True,
                                    hostname="progmmo")
    print("launching container")

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
