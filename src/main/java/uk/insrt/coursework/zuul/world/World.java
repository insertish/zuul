package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.Event;
import uk.insrt.coursework.zuul.events.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.events.EventSystem;

public class World {
    protected Map<String, Room> rooms = new HashMap<>();
    protected Map<String, Entity> entities = new HashMap<>();
    protected EntityPlayer player;

    protected EventSystem eventSystem;

    public World() {
        this.eventSystem = new EventSystem();
        this.player = new EntityPlayer(this);
        this.entities.put("player", this.player);
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public Room getRoom(String room) {
        return this.rooms.get(room);
    }

    public List<Entity> getEntitiesInRoom(Room room) {
        return this
            .entities
            .values()
            .stream()
            .filter(e -> e.getRoom() == room)
            .collect(Collectors.toList());
    }

    protected void registerDefaultEvents() {
        this.eventSystem.addListener(EventEntityEnteredRoom.class,
            (EventEntityEnteredRoom event) -> {
                Entity entity = event.getEntity();
                if (entity instanceof EntityPlayer) {
                    Room room = entity.getRoom();
                    System.out.println(
                        room.describe()
                            + "\nYou may go in "
                            + room.getDirections().size()
                            + " directions: "
                            + room.getDirections()
                                .stream()
                                .map(x -> x.toString().toLowerCase())
                                .collect(Collectors.joining(", "))
                    );
                }
            });
    }

    protected void linkRooms() {
        for (Room room : this.rooms.values()) {
            room.linkRooms();
        }
    }

    public Entity findEntity(String name) {
        List<Entity> entities = this.getEntitiesInRoom(this.getPlayer().getRoom());
        for (Entity entity : entities) {
            String[] aliases = entity.getAliases();
            for (String alias : aliases) {
                if (name.equalsIgnoreCase(alias)) {
                    return entity;
                }
            }
        }

        return null;
    }

    public void emit(Event event) {
        this.eventSystem.emit(event);
    }

    /**
     * Try to spawn the player in the first available room.
     */
    public void spawnPlayer() {
        this.player.setLocation(this.rooms.values().iterator().next());
    }
}
