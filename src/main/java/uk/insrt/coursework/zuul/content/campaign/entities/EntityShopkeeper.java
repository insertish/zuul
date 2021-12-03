package uk.insrt.coursework.zuul.content.campaign.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueNode;
import uk.insrt.coursework.zuul.dialogue.DialogueOption;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Shop keeper which the player can buy items from in the town.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityShopkeeper extends EntityNpc {
    private HashMap<Stage, Entity[]> items;
    private HashMap<Entity, Integer> stock;
    private HashMap<Entity, Integer> price;
    private HashMap<Entity, IEntityFactory> entityFactory;

    /**
     * Construct a new EntityShopkeeper.
     * @param world World
     * @param location Location
     */
    public EntityShopkeeper(World world, Location location) {
        super(world, location,
            "npc_shopkeeper",
            "<shop.npc.description>",
            new String[] { "shopkeeper", "shop", "keeper" });
        
        this.items = new HashMap<>();
        this.stock = new HashMap<>();
        this.price = new HashMap<>();
        this.entityFactory = new HashMap<>();

        this.createItems(world);
    }

    /**
     * Interface implemented by entity factories for producing entities.
     */
    private interface IEntityFactory {
        /**
         * Produce a new Entity of a certain type.
         * @return New entity
         */
        public Entity produce();
    }
    
    /**
     * Generate all the items and populate the data.
     * @param world World to place items in
     */
    private void createItems(World world) {
        EntityObject itemBoatKey = new EntityBoatKey(world, new Location());
        this.stock.put(itemBoatKey, 1);
        this.price.put(itemBoatKey, 39_260);
        this.entityFactory.put(itemBoatKey, () -> new EntityBoatKey(world, new Location()));

        EntityComms itemComms = new EntityComms(world, new Location());
        this.stock.put(itemComms, 3);
        this.price.put(itemComms, 3_100);
        this.entityFactory.put(itemComms, () -> new EntityComms(world, new Location()));

        EntityObject itemCat = new EntityObject(world, new Location(), 5, "", "<shop.npc.fake_item.cat>");
        this.stock.put(itemCat, 0);
        this.price.put(itemCat, 21_300);

        this.items.put(Stage.Exposition, new Entity[] {  });
        this.items.put(Stage.Recon, new Entity[] { itemBoatKey, itemComms });
        this.items.put(Stage.Stealth, new Entity[] { itemBoatKey, itemComms, itemCat });
        this.items.put(Stage.End, new Entity[] { itemBoatKey, itemCat });
    }

    /**
     * Index dialogue node, displays things that the player can buy.
     * Implicitly takes the context of the EntityShopkeeper.
     */
    private class IndexNode extends DialogueNode<String> {
        /**
         * Construct a new IndexNode.
         * We may ignore the description since we override {@code #getDescription}.
         */
        public IndexNode() {
            super(null);
        }

        @Override
        public String getDescription() {
            var w = (CampaignWorld) world;
            return "<shop.npc.greeting."
                + w.getStoryFlags().getStage().toString()
                + ">\n"
                + "<shop.npc.currently_have_amount_of_money> ¥ "
                + w.getStoryFlags().getBalance()
                + "\n";
        }

        @Override
        protected List<DialogueOption<String>> getOptions() {
            ArrayList<DialogueOption<String>> options = new ArrayList<>();
            
            var w = (CampaignWorld) world;
            var flags = w.getStoryFlags();
            var player = w.getPlayer();

            // Get all the items we can access at this story stage.
            Entity[] list = items.get(flags.getStage());
            for (Entity item : list) {
                int count = stock.get(item);
                int cost = price.get(item);
                IEntityFactory factory = entityFactory.get(item);

                // Add the option for this item.
                options.add(new DialogueOption<String>(
                    item.describe()
                        + " ["
                        + Ansi.Yellow
                        + item.getWeight()
                        + " kg"
                        + Ansi.Reset
                        + "] (¥ "
                        + Ansi.Green
                        + cost
                        + Ansi.Reset
                        + ") - "
                        + (count == 0 ?
                              "<shop.npc.out_of_stock>!"
                            : count + " <shop.npc.x_left>"),
                    io -> {
                        // Check that this item is in stock.
                        if (count == 0) {
                            io.println("\n\n<shop.npc.item_out_of_stock.1> "
                                + Ansi.Red
                                + "<shop.npc.out_of_stock>"
                                + Ansi.Reset
                                + ", <shop.npc.item_out_of_stock.2>!");
                        } else {
                            // Make sure the player can hold this item without
                            // going over their inventory weight limit.
                            Inventory inv = player.getInventory();
                            if (inv.getWeight() + item.getWeight() > inv.getMaxWeight()) {
                                io.println("\n\n<shop.npc.too_heavy>");
                            } else {
                                // Try to deduct money from the player's money.
                                if (flags.deductFromBalance(cost)) {
                                    io.println("\n\n<shop.npc.bought.1> "
                                        + item.getHighlightedName()
                                        + " <shop.npc.bought.2>!");
                                    
                                    stock.put(item, count - 1);

                                    Entity entity = factory.produce();
                                    entity.setLocation(inv);
                                } else {
                                    io.println("\n\n<shop.npc.not_enough> "
                                        + item.getHighlightedName() + "!");
                                }
                            }
                        }

                        return "index";
                    }
                ));
            }

            options.add(new DialogueOption<String>("<shop.npc.leave>", "index").mustExit());
            return options;
        }
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        dialogue.addPart("index", new IndexNode());
        dialogue.setNodeIfPresent("index");
    }
}
