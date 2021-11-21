package uk.insrt.coursework.zuul.content.campaign;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityCat;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.events.EventEntityLeftRoom;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

// https://democracy.york.gov.uk/documents/s2116/Annex%20C%20REcycling%20Report%20frnweights2005.pdf
// https://www.google.com/maps/@50.4293559,18.9742453,16.12z
// https://twitter.com/Yarung3/status/1258670295520628736/photo/1
// https://twitter.com/jgilleard/status/1242354985351786497
// [3:02] https://brand-new-animal.fandom.com/wiki/Runaway_Raccoon

public class CampaignWorld extends World {
    private ArrayList<Room> visitedRooms;

    public CampaignWorld(IOSystem io) {
        super(io);

        this.visitedRooms = new ArrayList<>();

        this.buildWorld();
        this.spawnEntities();
        this.registerEvents();
    }

    public boolean hasVisited(Room room) {
        return this.visitedRooms.contains(room);
    }

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

    private void spawnEntities() {
        for (Room room : this.rooms.values()) {
            room.spawnEntities();
        }
    }

    /**
     * Register all the game logic
     */
    private void registerEvents() {
        super.registerDefaultEvents();

        this.eventSystem.addListener(EventEntityEnteredRoom.class, (IEventListener<EventEntityEnteredRoom>) this.getRoom("Worm Hole"));
        this.eventSystem.addListener(EventEntityEnteredRoom.class,
            (EventEntityEnteredRoom event) -> {
                Entity entity = event.getEntity();
                if (entity instanceof EntityPlayer) {
                    Room room = entity.getRoom();

                    // Mark current room as previously visited.
                    this.visitedRooms.add(room);

                    // When we enter a new room, list what we can see.
                    String entities = this.getEntitiesInRoom(entity.getRoom())
                        .stream()
                        .filter(e -> !(e instanceof EntityPlayer))
                        .map(e -> "- " + e.describe() + " (\u001B[40m\u001B[37m" + e.getName() + "\u001B[0m)")
                        .collect(Collectors.joining("\n"));

                    if (entities.length() > 0) {
                        this.io.println("You can see:\n" + entities);
                    }
                } else {
                    // If another entity enters the room,
                    // conditionally mention this to the player.
                    EntityPlayer player = this.getPlayer();
                    if (entity.getRoom() == player.getRoom()) {
                        if (entity instanceof EntityCat) {
                            this.io.println("\nA cat has wandered in.");
                        }
                    }
                }
            });

        this.eventSystem.addListener(EventEntityLeftRoom.class,
            (EventEntityLeftRoom event) -> {
                Entity entity = event.getEntity();
                if (entity instanceof EntityPlayer) return;

                Room room = event.getRoom();
                if (room != this.player.getRoom()) return;

                // If another entity leaves the room,
                // conditionally mention this to the player.
                if (entity instanceof EntityCat) {
                    this.io.println("\nYou see a cat leave.");
                }
            });
    }

    @Override
    public void spawnPlayer() {
        this.player.setLocation(this.rooms.get("City Centre"));
    }
}
