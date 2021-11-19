package uk.insrt.coursework.zuul.content.campaign;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.behaviours.SimpleWanderAI;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityBoat;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityCat;
import uk.insrt.coursework.zuul.entities.EntityNPC;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.events.EventEntityLeftRoom;
import uk.insrt.coursework.zuul.events.EventTick;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class CampaignWorld extends World {
    private ArrayList<Room> visitedRooms;

    public CampaignWorld() {
        super();
        this.visitedRooms = new ArrayList<>();

        this.buildWorld();
        this.spawnEntities();
        this.registerEvents();
    }

    private void buildWorld() {
        // https://democracy.york.gov.uk/documents/s2116/Annex%20C%20REcycling%20Report%20frnweights2005.pdf
        // https://www.google.com/maps/@50.4293559,18.9742453,16.12z
        // https://twitter.com/Yarung3/status/1258670295520628736/photo/1
        // https://twitter.com/jgilleard/status/1242354985351786497
        // [3:02] https://brand-new-animal.fandom.com/wiki/Runaway_Raccoon

        this.addRoom(
            new Room("City Centre") {
                public String describe() {
                    if (visitedRooms.contains(this)) {
                        return "you've been here before";
                    }

                    return "something something long description.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("Back Alley"));
                    this.setAdjacent(Direction.NORTH_WEST, getRoom("Street"));
                    this.setAdjacent(Direction.WEST, getRoom("Apartments: Reception"));
                    this.setAdjacent(Direction.SOUTH, getRoom("Coastline"));
                }

                public void spawnEntities(World world, Location location) {
                    spawnEntity("cat", new EntityCat(world, location));
                }
            }
        );

        this.addRoom(
            new Room("Street") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.SOUTH, getRoom("Apartments: Reception"));
                    this.setAdjacent(Direction.EAST, getRoom("City Centre"));
                    this.setAdjacent(Direction.NORTH, getRoom("Shop"));
                    this.setAdjacent(Direction.WEST, getRoom("Medical Centre: Reception"));
                }
            }
        );

        this.addRoom(
            new Room("Medical Centre: Reception") {
                public String describe() {
                    return "You're now at the Medical Centre's reception.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.EAST, getRoom("Street"));
                    this.setAdjacent(Direction.DOWN, getRoom("Medical Centre: Office"));
                }

                public boolean canLeave(Direction direction) {
                    if (direction == Direction.DOWN) {
                        if (getEntitiesInRoom(this)
                            .contains(getEntity("guard1"))) {
                            System.out.println("There is security watching the stairs, there's no way to get past them.");
                            return false;
                        }
                    }

                    return true;
                }

                public void spawnEntities(World world, Location location) {
                    world.spawnEntity("guard1", new EntityNPC(world, location) {
                        @Override
                        public String[] getAliases() {
                            return new String[] { "security guard", "guard" };
                        }

                        @Override
                        public String describe() {
                            return null;
                        }
                    });
                }
            }
        );

        this.addRoom(
            new Room("Medical Centre: Office") {
                public String describe() {
                    return "You find yourself at the Medical Centre's office.\nYou definitely shouldn't be here...";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.UP, getRoom("Medical Centre: Reception"));
                }
            }
        );

        this.addRoom(
            new Room("Shop") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.SOUTH, getRoom("Street"));
                }
            }
        );

        this.addRoom(
            new Room("Apartments: Reception") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("Street"));
                    this.setAdjacent(Direction.EAST, getRoom("City Centre"));
                    this.setAdjacent(Direction.UP, getRoom("Apartments: Home"));
                }
            }
        );

        this.addRoom(
            new Room("Apartments: Home") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.DOWN, getRoom("Apartments: Reception"));
                }

                public void spawnEntities(World world, Location location) {
                    world.spawnEntity("bed", new EntityObject(world, location, 80, new String[] { "bed" }, "Bed") {
                        @Override
                        public boolean use(Entity target) {
                            // for example, we could move the world forwards by 20 ticks
                            for (int i=0;i<20;i++) {
                                world.emit(new EventTick());
                            }

                            System.out.println("You take a nap.");
                            return true;
                        }
                    });

                    world.spawnEntity("laptop", new EntityObject(world, location, 2, new String[] { "laptop" }, "Laptop"));
                }
            }
        );

        this.addRoom(
            new Room("Back Alley") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.SOUTH, getRoom("City Centre"));
                }
            }
        );

        this.addRoom(
            new Room("Coastline") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("City Centre"));
                }

                public void spawnEntities(World world, Location location) {
                    world.spawnEntity("boat1",
                        new EntityBoat(world, location,
                            world.getRoom("Mainland: Coastline")));
                }
            }
        );

        this.addRoom(
            new Room("Mainland: Coastline") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.SOUTH, getRoom("Forest"));
                }

                public void spawnEntities(World world, Location location) {
                    world.spawnEntity("boat2",
                        new EntityBoat(world, location,
                            world.getRoom("Coastline")));
                }
            }
        );

        this.addRoom(
            new Room("Forest") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("Mainland: Coastline"));
                    this.setAdjacent(Direction.EAST, getRoom("Worm Hole"));
                }
            }
        );

        this.addRoom(
            new Room("Worm Hole") {
                public String describe() {
                    return this.getName();
                }

                protected void setupDirections() {}
            }
        );

        this.linkRooms();

        if (this.rooms.size() > 12) {
            System.out.println("There are " + this.rooms.size() + " rooms. Currently over by " + (this.rooms.size() - 12) + ".");
        }
    }

    private void spawnEntities() {
        for (Room room : this.rooms.values()) {
            room.spawnEntities(this, new Location(room));
        }
    }

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
                        final String[] locations = new String[] {
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
