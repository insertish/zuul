package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.Map;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;

public class World {
    private Map<String, Room> rooms = new HashMap<>();
    private Map<String, Entity> entities = new HashMap<>();
    private EntityPlayer player;

    public World() {
        this.buildWorld();
        this.spawnEntities();
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

    private void spawnEntities() {
        this.player = new EntityPlayer(this);
        this.entities.put("player", this.player);
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public Room getRoom(String room) {
        return this.rooms.get(room);
    }

    public Entity[] getEntitiesInRoom(Room room) {
        return (Entity[]) this
            .entities
            .values()
            .stream()
            .filter(e -> e.isInRoom(room))
            .toArray();
    }
}
