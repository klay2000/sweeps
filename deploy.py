import docker
client = docker.from_env()


# make sure host has all necessary containers
def check_dependencies(deps):
    host_images = [ n.tags[0] for n in client.images.list() ]
    for i in deps:
        if not(i in host_images):
            print("pulling image: ", i)
            client.images.pull(i)


dependencies = ["mongo:latest"]
check_dependencies(dependencies)

# build the container. This was originally in check_dependencies, but I think that the container should be rebuilt on every deploy
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
mongo = client.containers.run("mongo:latest", detach=True)
dblan.connect(mongo)
client.containers.run("progmmo:latest")
dblan.connect(client.containers.get("progmmo:latest"))
