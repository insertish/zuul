package uk.insrt.coursework.zuul.content.campaign;

import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityCat;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityWithDialogue;
import uk.insrt.coursework.zuul.content.campaign.events.EventGameStageChanged;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomApartmentsHome;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomApartmentsReception;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomBackAlley;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomCityCentre;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomCoastline;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomForest;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomMainlandCoastline;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomMedicalCentreOffice;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomMedicalCentreReception;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomShop;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomStreet;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomWormHole;
import uk.insrt.coursework.zuul.dialogue.DialogueLoader;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.events.world.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.events.world.EventEntityLeftRoom;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * The main campaign World.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class CampaignWorld extends World {
    private StoryFlags flags;
    private HashSet<Room> visitedRooms;
    private DialogueLoader dialogueLoader;

    /**
     * Construct a new Campaign World
     * @param io Provided IO system
     */
    public CampaignWorld(IOSystem io) {
        super(io);

        this.visitedRooms = new HashSet<>();
        this.dialogueLoader = new DialogueLoader();
        this.flags = new StoryFlags(this.getEventSystem());
        
        try {
            this.dialogueLoader.load("/dialogue.toml");
        } catch (IOException e) {
            System.err.println("Failed to load resources for campaign world!");
            e.printStackTrace();
        }

        this.buildWorld();
        this.spawnEntities();
        this.registerEvents();
    }

    /**
     * Get this World's Dialogue Loader
     * @return Dialogue Loader
     */
    public DialogueLoader getDialogueLoader() {
        return this.dialogueLoader;
    }

    /**
     * Get the global story flags.
     * @return Story flags instance
     */
    public StoryFlags getStoryFlags() {
        return this.flags;
    }

    /**
     * Check whether the Player has visited a certain Room yet
     * @param room Room to check
     * @return True if the Player has visited the given Room
     */
    public boolean hasVisited(Room room) {
        return this.visitedRooms.contains(room);
    }

    /**
     * Get a rounded whole number percentage of how much the World has been explored.
     * @return Integer representing percentage of World explored
     */
    public int percentVisited() {
        return Math.round((float) this.visitedRooms.size() / this.rooms.size() * 100.0f);
    }

    /**
     * Create all the Worlds and link adjacent Rooms together.
     */
    private void buildWorld() {
        final Room[] rooms = {
            new RoomCityCentre(this),
            new RoomStreet(this),
            new RoomShop(this),
            new RoomBackAlley(this),
            new RoomApartmentsReception(this),
            new RoomApartmentsHome(this),
            new RoomMedicalCentreReception(this),
            new RoomMedicalCentreOffice(this),
            new RoomCoastline(this),
            new RoomMainlandCoastline(this),
            new RoomForest(this),
            new RoomWormHole(this)
        };

        for (Room room : rooms) {
            this.addRoom(room);
        }

        this.linkRooms();
    }

    /**
     * Spawn and setup any Entities within this World.
     */
    private void spawnEntities() {
        for (Room room : this.rooms.values()) {
            room.spawnEntities();
        }

        // Entangle boat inventories.
        Entity boat1 = this.entities.get("boat1");
        Entity boat2 = this.entities.get("boat2");
        boat1.entangleInventory(boat2.getInventory());
    }

    /**
     * Register all the game logic.
     */
    private void registerEvents() {
        // Capture all Events for Entities entering Rooms.
        this.eventSystem.addListener(EventEntityEnteredRoom.class,
            (EventEntityEnteredRoom event) -> {
                Entity entity = event.getEntity();
                if (entity instanceof EntityPlayer) {
                    Room room = entity.getRoom();

                    // Whenever the Player enters a Room, we should print the
                    // description of the Room and list things found in the Room.
                    this.io.println(
                        room.describe()
                            + "\n<global.can_go_in_x_directions.1> "
                            + room.getDirections().size()
                            + " <global.can_go_in_x_directions.2>: "
                            + room.getDirections()
                                .stream()
                                .map(x ->
                                    x.toString()
                                     .toLowerCase()
                                     .replaceAll("_", " ")
                                )
                                .collect(Collectors.joining(", "))
                    );

                    // Mark current room as previously visited.
                    this.visitedRooms.add(room);

                    // When we enter a new room, list what we can see.
                    String entities = this.getEntitiesInRoom(entity.getRoom())
                        .stream()
                        .filter(e -> !(e instanceof EntityPlayer))
                        .map(e -> "- " + e.describe() + " ("
                            + Ansi.BackgroundPurple + Ansi.Black
                            + e.getName() + Ansi.Reset + ")")
                        .collect(Collectors.joining("\n"));

                    if (entities.length() > 0) {
                        this.io.println("<global.sight>\n" + entities);
                    }
                } else {
                    // If another entity enters the room,
                    // conditionally mention this to the player.
                    EntityPlayer player = this.getPlayer();
                    if (entity.getRoom() == player.getRoom()) {
                        if (entity instanceof EntityCat) {
                            this.io.println("\n<entities.cat.enter>");
                        }
                    }
                }
            });

        // Capture all Events for Entities leaving Rooms.
        this.eventSystem.addListener(EventEntityLeftRoom.class,
            (EventEntityLeftRoom event) -> {
                Entity entity = event.getEntity();
                if (entity instanceof EntityPlayer) return;

                Room room = event.getRoom();
                if (room != this.player.getRoom()) return;

                // If another entity leaves the room,
                // conditionally mention this to the player.
                if (entity instanceof EntityCat) {
                    this.io.println("\n<entities.cat.leave>");
                }
            });
        
        // Register event for game stage changing.
        this.eventSystem.addListener(EventGameStageChanged.class,
            (EventGameStageChanged event) -> {
                Stage stage = event.getStage();
                switch (stage) {
                    case Recon: {
                        for (Entity entity : this.entities.values()) {
                            if (entity instanceof EntityWithDialogue) {
                                ((EntityWithDialogue<?>) entity).setDialogueNodeIfPresent("recon");
                            }
                        }
                        break;
                    }
                    case End: {
                        io.println("<stage.reached_conclusion>");
                        break;
                    }
                    default: break;
                }
            });

        // Register required Events for Worm Hole room to function.
        @SuppressWarnings("unchecked")
        var wh = (IEventListener<EventEntityEnteredRoom>) this.getRoom("Worm Hole");
        this.eventSystem.addListener(EventEntityEnteredRoom.class, wh);

        // Register required Events for the protestors to disappear.
        @SuppressWarnings("unchecked")
        var st = (IEventListener<EventGameStageChanged>) this.getRoom("Street");
        this.eventSystem.addListener(EventGameStageChanged.class, st);
    }

    @Override
    public void spawnPlayer() {
        this.player.setLocation(this.rooms.get("Apartments: Home"));
    }
}
