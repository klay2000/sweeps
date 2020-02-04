import argparse
import docker
client = docker.from_env()
host_images = [ n.tags[0] for n in client.images.list() ]
host_containers = [ n.name for n in client.containers.list() ]


# make sure host has all necessary containers
def check_dependencies(deps):
    for i in deps:
        if not(i in host_images):
            print("pulling image: ", i)
            client.images.pull(i)

def clean_containers():
    for c in host_containers:
        client.containers.get(c).remove(force=True)

def clean_images():
    for i in host_images:
        client.images.get(i).remove(force=True)


def main(containerClean=False, imageClean=False):
    if containerClean:
        clean_containers()
    if imageClean:
        clean_images()

    dependencies = ["mongo:latest", "openjdk:latest"]
    check_dependencies(dependencies)
    # build the container. This was originally in check_dependencies, but I think that the container should be rebuilt on every deploy
    if not("progmmo:latest" in host_images):
        print("building progmmo container")
        client.images.build(path=".", tag="progmmo:latest")
        print("container built successfully")
        # docker will create duplicate networks without protest so this check needs to be here
        networkExistsFlag = False
        for network in client.networks.list():
            if network.name == "dblan":
                networkExistsFlag = True
        if networkExistsFlag == False:
            client.networks.create("dblan", driver="bridge", internal=True)
            print("creating network")
        # assmble everything into networks
        dblanar = list(filter(lambda n: n.name == "dblan", client.networks.list()))
        dblan = dblanar[0]
        if not("mongo:latest" in host_containers):
            mongo = client.containers.run("mongo:latest", detach=True)
        dblan.connect(mongo)
        progmmo = client.containers.run("progmmo:latest")
        dblan.connect(client.containers.get("progmmo:latest"))
        print(progmmo.logs())



if __name__ =="__main__":
    parser = argparse.ArgumentParse()
    parser.add_argument('--image',
    parser.add_argument('--container',
    main()
