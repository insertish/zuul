package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.Event;
import uk.insrt.coursework.zuul.events.EventSystem;
import uk.insrt.coursework.zuul.io.IOSystem;

/**
 * Representation of the game World.
 * Contains all the Rooms and Entities as well as the Player.
 * Has its own Event system for signaling when things should happen.
 * Also has access to the IO system which is provided to all Rooms and Entities.
 */
public class World {
    protected Map<String, Room> rooms = new HashMap<>();
    protected Map<String, Entity> entities = new HashMap<>();
    protected EntityPlayer player;

    protected IOSystem io;
    protected EventSystem eventSystem;

    /**
     * Consturct a new game World with a given IO system.
     * @param io IO system to provide to everything
     */
    public World(IOSystem io) {
        this.io = io;
        this.eventSystem = new EventSystem();
        this.player = new EntityPlayer(this);
        this.entities.put("player", this.player);
    }

    /**
     * Find an Entity by its ID.
     * @param id Entity ID
     * @return Entity if it exists, otherwise null.
     */
    public Entity getEntity(String id) {
        return this.entities.get(id);
    }

    /**
     * Find an Room by its ID.
     * @param room Room ID
     * @return Room if it exists, otherwise null.
     */
    public Room getRoom(String room) {
        return this.rooms.get(room);
    }

    /**
     * Get the Player entity.
     * @return The player entity
     */
    public EntityPlayer getPlayer() {
        return this.player;
    }

    /**
     * Get the IO system provided to this World.
     * @return IO system
     */
    public IOSystem getIO() {
        return this.io;
    }

    /**
     * Get this World's event system.
     * @return World event system
     */
    public EventSystem getEventSystem() {
        return this.eventSystem;
    }

    /**
     * Add a Room to this World.
     * @param room Room to add
     */
    protected void addRoom(Room room) {
        this.rooms.put(room.getName(), room);
    }

    /**
     * Spawn a new Entity in the World.
     * @param id Unique Entity ID
     * @param entity Entity to spawn
     */
    public void spawnEntity(String id, Entity entity) {
        this.entities.put(id, entity);
    }

    /**
     * Get all the Entities found in a given Room.
     * @param room Room to search for
     * @return List of Entities in the World in a given Room
     */
    public List<Entity> getEntitiesInRoom(Room room) {
        return this
            .entities
            .values()
            .stream()
            .filter(e -> e.getRoom() == room)
            .collect(Collectors.toList());
    }

    protected void linkRooms() {
        for (Room room : this.rooms.values()) {
            room.linkRooms();
        }
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
