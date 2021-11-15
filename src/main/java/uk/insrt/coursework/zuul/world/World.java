package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.Map;

public class World {
    private Map<String, Room> rooms = new HashMap<>();

    public World() {
        this.buildWorld();
    }

    private void addRoom(Room room) {
        this.rooms.put(room.getName(), room);
    }

    private void buildWorld() {
        this.addRoom(
            new Room("starting") {
                public String getAdjacentRoom(Direction dir) {
                    if (dir == Direction.North) {
                        return "next door";
                    }

                    return null;
                }
            }
        );
            
        this.addRoom(
            new Room("next door") {
                public String getAdjacentRoom(Direction dir) {
                    if (dir == Direction.South) {
                        return "starting";
                    }

                    return null;
                }
            }
        );
    }
}
