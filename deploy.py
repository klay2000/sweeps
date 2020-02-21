#!/usr/bin/env python

import argparse
import docker

client = docker.from_env()
llAPI = docker.APIClient(base_url='unix://var/run/docker.sock')
host_images = [ n for n in client.images.list(all=True) if not(n.tags == [])]
host_containers = [ n for n in client.containers.list(all=True) ]


def init_mongo(ip='172.17.0.2'):
    print("initializing mongo")
    if "mongodb" in [n.name for n in client.containers.list(all=True)]:
        return client.containers.get("mongodb")
    else:
        return client.containers.run("mongo",
                                      hostname="mongodb",
                                      network="dblan",
                                      name="mongodb",
                                      detach=True)

# TODO: use this
def init_volume():
    print("Verifying storage volume")
    return client.volumes.create("database", driver='local') if not("database" in [ n.name for n in client.volumes.list() ]) else client.volumes.get("database")

# make sure host has all necessary containers
def check_dependencies(deps):
        for i in deps:
            if not(i in [ i.tags[0] for i in host_images ]):
                print("pulling image: ", i)
                client.images.pull(i)


# needs to be either "containers", "images", "networks", or "volumes"
def clean(mode, force=False):
    print("cleaning", mode)
    targets = getattr(client, mode).list() if mode=="volumes" or mode=="networks" else getattr(client, mode).list(all=True)
    for i in targets:
        try:
            if force:
                i.remove(force=True)
            else:
                i.remove()
        except:
            continue

def init_networks():
    dblan = client.networks.create("dblan", driver="overlay", internal=False, check_duplicate=True, attachable=True)
    print(dblan)
    return dblan

def main():
    check_dependencies(["mongo:latest", "openjdk:latest"])
    if not("progmmo" in host_images):
        print("building progmmo container")
        client.images.build(path=".", tag="progmmo")
        print("container built successfully")

    try:
        nets = init_networks()
    except:
        nets = client.networks.list()

    mongo = init_mongo()
    progmmo = client.containers.run("progmmo", network="dblan", ports={'8080/tcp':8080, '443/tcp':443}, name="progmmo", detach=True, hostname="progmmo")
    print("launching container")

if __name__ == "__main__":
    # TODO: the parameters should really just be --clean (mode) and --nolaunch
    parser = argparse.ArgumentParser()
    parser.add_argument('--images', help="clean host images before running", action="store_true")
    parser.add_argument('--containers', help="clean host containers before running", action="store_true")
    parser.add_argument('--volumes', help="clean host volumes before running", action="store_true")
    parser.add_argument('--networks', help="clean host networks before running", action="store_true")
    parser.add_argument('--nolaunch', help="do not launch the new containers, only clean the existing ones and exit", action="store_true")
    args = parser.parse_args()

    if args.containers:
        clean("containers", force=True)

    if args.images:
        clean("images", force=True)

    if args.volumes:
        clean("volumes", force=True)

    if args.networks:
        clean("networks")

    if not(args.nolaunch):
        main()
