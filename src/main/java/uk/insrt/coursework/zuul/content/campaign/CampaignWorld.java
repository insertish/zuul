package uk.insrt.coursework.zuul.content.campaign;

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
    public CampaignWorld() {
        super();
        this.buildWorld();
        this.spawnEntities();
        this.registerEvents();
    }

    private void addRoom(Room room) {
        this.rooms.put(room.getName(), room);
    }

    private void buildWorld() {
        World world = this;

        this.addRoom(
            new Room("starting") {
                public String describe() {
                    return "This is the starting area.";
                }

                protected void setupDirections() {
                    this.adjacentRooms.put(Direction.NORTH, world.getRoom("next door"));
                }
            }
        );
            
        this.addRoom(
            new Room("next door") {
                public String describe() {
                    return "This is the area next to the starting area.";
                }

                protected void setupDirections() {
                    this.adjacentRooms.put(Direction.SOUTH, world.getRoom("starting"));
                }
            }
        );

        this.linkRooms();
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
                    String entities = this.getEntitiesInRoom(entity.getRoom())
                        .stream()
                        .filter(e -> !(e instanceof EntityPlayer))
                        .map(e -> "- " + e.describe())
                        .collect(Collectors.joining("\n"));

                    if (entities.length() > 0) {
                        System.out.println("You can see:\n" + entities);
                    }
                } else {
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
        this.player.setLocation(this.rooms.get("starting"));
    }
}
