package progmmo.server.command;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import progmmo.server.utils.CommandType;
import progmmo.server.utils.Direction;

@Document
public class Command {

    private Direction direction;
    private CommandType commandType;

    @Indexed(unique=true, name="Command_Entity_Index" )
    private String EntityID;

    public Command(Direction direction, CommandType commandType, String entityID) {
        this.direction = direction;
        this.commandType = commandType;
        EntityID = entityID;
    }

    public Direction getDirection() {
        return direction;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getEntityID() {
        return EntityID;
    }
}
