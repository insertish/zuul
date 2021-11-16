package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.Map;

import uk.insrt.coursework.zuul.entities.Entity;

public class World {
    private Map<String, Room> rooms = new HashMap<>();
    private Entity player;

    public World() {
        this.buildWorld();
        this.player = new Entity(this, "starting");
    }

    private void addRoom(Room room) {
        this.rooms.put(room.getName(), room);
    }

    private void buildWorld() {
        this.addRoom(
            new Room("starting") {
                public String getAdjacentRoom(Direction dir) {
                    if (dir == Direction.NORTH) {
                        return "next door";
                    }

                    return null;
                }
            }
        );
            
        this.addRoom(
            new Room("next door") {
                public String getAdjacentRoom(Direction dir) {
                    if (dir == Direction.SOUTH) {
                        return "starting";
                    }

                    return null;
                }
            }
        );
    }

    public Entity getPlayer() {
        return this.player;
    }

    public Room getRoom(String room) {
        return this.rooms.get(room);
    }
}
