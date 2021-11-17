package uk.insrt.coursework.zuul.content.campaign;

import java.util.ArrayList;
import java.util.stream.Collectors;

import uk.insrt.coursework.zuul.behaviours.SimpleWanderAI;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityCat;
import uk.insrt.coursework.zuul.entities.EntityPlayer;
import uk.insrt.coursework.zuul.events.EventEntityEnteredRoom;
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

    private void addRoom(Room room) {
        this.rooms.put(room.getName(), room);
    }

    private void buildWorld() {
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
                    this.setAdjacent(Direction.WEST, getRoom("Apartments"));
                    this.setAdjacent(Direction.SOUTH, getRoom("Coastline"));
                }
            }
        );

        this.addRoom(
            new Room("Street") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.SOUTH, getRoom("Apartments"));
                    this.setAdjacent(Direction.EAST, getRoom("City Centre"));
                    this.setAdjacent(Direction.NORTH, getRoom("Shop"));
                }
            }
        );

        this.addRoom(
            new Room("Medical Centre: Reception") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.EAST, getRoom("Street"));
                }
            }
        );

        this.addRoom(
            new Room("Shop") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.SOUTH, getRoom("Street"));
                }
            }
        );

        this.addRoom(
            new Room("Apartments: Reception") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("Street"));
                    this.setAdjacent(Direction.EAST, getRoom("City Centre"));
                }
            }
        );

        this.addRoom(
            new Room("Back Alley") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("Street"));
                    this.setAdjacent(Direction.EAST, getRoom("City Centre"));
                }
            }
        );

        this.addRoom(
            new Room("Coastline") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.setAdjacent(Direction.NORTH, getRoom("City Centre"));
                }
            }
        );

        this.linkRooms();

        System.out.println("There are " + this.rooms.size() + " rooms. Køłłing will permit " + (12 - this.rooms.size()) + " more.");
    }

    private void spawnEntities() {
        this.entities.put("cat", new EntityCat(this, new Location(this.rooms.get("starting"))));
    }

    private void registerEvents() {
        super.registerDefaultEvents();

        this.eventSystem.onTick(new SimpleWanderAI(
            this.entities.get("cat"),
            new Room[] {
                this.rooms.get("starting"),
                this.rooms.get("next door")
            },
            8
        ));

        this.eventSystem.addListener(EventEntityEnteredRoom.class,
            (EventEntityEnteredRoom event) -> {
                Entity entity = event.getEntity();
                if (entity instanceof EntityPlayer) {
                    // Mark current room as previously visited.
                    this.visitedRooms.add(entity.getRoom());

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
    }

    @Override
    public void spawnPlayer() {
        this.player.setLocation(this.rooms.get("City Centre"));
    }
}
