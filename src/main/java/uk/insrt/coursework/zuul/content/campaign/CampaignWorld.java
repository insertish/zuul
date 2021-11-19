package uk.insrt.coursework.zuul.content.campaign;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.behaviours.SimpleWanderAI;
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
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

// https://democracy.york.gov.uk/documents/s2116/Annex%20C%20REcycling%20Report%20frnweights2005.pdf
// https://www.google.com/maps/@50.4293559,18.9742453,16.12z
// https://twitter.com/Yarung3/status/1258670295520628736/photo/1
// https://twitter.com/jgilleard/status/1242354985351786497
// [3:02] https://brand-new-animal.fandom.com/wiki/Runaway_Raccoon

public class CampaignWorld extends World {
    private ArrayList<Room> visitedRooms;

    public CampaignWorld() {
        super();
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

        this.eventSystem.onTick(new SimpleWanderAI(
            this.entities.get("cat"),
            new Room[] {
                this.rooms.get("City Centre"),
                this.rooms.get("Street"),
                this.rooms.get("Shop"),
                this.rooms.get("Street"),
                this.rooms.get("City Centre"),
                this.rooms.get("Back Alley"),
                this.rooms.get("City Centre")
            },
            8
        ));

        this.eventSystem.addListener(EventEntityEnteredRoom.class,
            (EventEntityEnteredRoom event) -> {
                Entity entity = event.getEntity();
                System.out.println("Entity " + entity.getAliases()[0] + " has entered room " + entity.getRoom().getName());

                if (entity instanceof EntityPlayer) {
                    Room room = entity.getRoom();

                    // Handle magic transporter (Worm Hole room)
                    if (room == this.rooms.get("Worm Hole")) {
                        final Random random = new Random();
                        final String[] locations = {
                            "City Centre",
                            "Coastline",
                            "Mainland: Coastline",
                            "Forest",
                            "Street",
                            "Back Alley"
                        };

                        System.out.println("\nYou step into the worm hole...\n");

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) { }

                        final int WIDTH = 42;

                        // Transport animation, this will take 1800 ms.
                        for (int i=0;i<5;i++) {
                            System.out.println("*".repeat(i*3) + "\\" + " ".repeat(WIDTH - i * 6 - 2) + "/" + "*".repeat(i*3));
                            try {
                                Thread.sleep(60);
                            } catch (Exception e) { }
                        }

                        for (int i=0;i<30;i++) {
                            var out = "";
                            for (int j=0;j<WIDTH;j++) {
                                out += random.nextInt(8) == 0 ? "*" : " ";
                            }

                            System.out.println(out);

                            try {
                                Thread.sleep(40);
                            } catch (Exception e) { }
                        }

                        for (int i=5;i>0;i--) {
                            System.out.println("*".repeat(i*3) + "/" + " ".repeat(WIDTH - i * 6 - 2) + "\\" + "*".repeat(i*3));
                            try {
                                Thread.sleep(60);
                            } catch (Exception e) { }
                        }

                        System.out.println();

                        String location = locations[random.nextInt(locations.length)];
                        Room target = this.getRoom(location);
                        entity.setLocation(target);
                        return;
                    }

                    // Mark current room as previously visited.
                    this.visitedRooms.add(room);

                    // When we enter a new room, list what we can see.
                    String entities = this.getEntitiesInRoom(entity.getRoom())
                        .stream()
                        .filter(e -> !(e instanceof EntityPlayer))
                        .map(e -> "- " + e.describe())
                        .collect(Collectors.joining("\n"));

                    if (entities.length() > 0) {
                        System.out.println("You can see:\n" + entities);
                    }
                } else {
                    // If another entity enters the room,
                    // conditionally mention this to the player.
                    EntityPlayer player = this.getPlayer();
                    if (entity.getRoom() == player.getRoom()) {
                        if (entity instanceof EntityCat) {
                            System.out.println("\nA cat has wandered in.");
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
                    System.out.println("\nYou see a cat leave.");
                }
            });
    }

    @Override
    public void spawnPlayer() {
        this.player.setLocation(this.rooms.get("City Centre"));
    }
}
