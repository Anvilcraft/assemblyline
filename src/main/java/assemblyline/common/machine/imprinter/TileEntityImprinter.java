package assemblyline.common.machine.imprinter;

import java.util.ArrayList;
import java.util.List;

import assemblyline.api.IArmbot;
import assemblyline.api.IArmbotUseable;
import assemblyline.common.Pair;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.TranslationHelper;
import universalelectricity.prefab.multiblock.TileEntityMulti;
import universalelectricity.prefab.tile.TileEntityAdvanced;

public class TileEntityImprinter
    extends TileEntityAdvanced implements ISidedInventory, IArmbotUseable {
    public static final int IMPRINTER_MATRIX_START = 9;
    public static final int INVENTORY_START = 12;
    public ItemStack[] craftingMatrix = new ItemStack[9];
    public ItemStack[] imprinterMatrix = new ItemStack[3];
    public ItemStack[] containingItems = new ItemStack[18];
    public ContainerImprinter container;
    private boolean isImprinting = false;
    public boolean searchInventories = true;

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return this.craftingMatrix.length + this.imprinterMatrix.length
            + this.containingItems.length;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        if (slot < this.getSizeInventory()) {
            if (slot < 9) {
                this.craftingMatrix[slot] = itemStack;
            } else if (slot < 12) {
                this.imprinterMatrix[slot - 9] = itemStack;
            } else {
                this.containingItems[slot - 12] = itemStack;
            }
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        if (this.getStackInSlot(i) != null) {
            if (this.getStackInSlot((int) i).stackSize <= amount) {
                ItemStack var3 = this.getStackInSlot(i);
                this.setInventorySlotContents(i, null);
                return var3;
            }
            ItemStack var3 = this.getStackInSlot(i).splitStack(amount);
            if (this.getStackInSlot((int) i).stackSize == 0) {
                this.setInventorySlotContents(i, null);
            }
            return var3;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < 9) {
            return this.craftingMatrix[slot];
        }
        if (slot < 12) {
            return this.imprinterMatrix[slot - 9];
        }
        return this.containingItems[slot - 12];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.getStackInSlot(slot) != null) {
            ItemStack var2 = this.getStackInSlot(slot);
            this.setInventorySlotContents(slot, null);
            return var2;
        }
        return null;
    }

    @Override
    public String getInventoryName() {
        return TranslationHelper.getLocal("tile.imprinter.name");
    }

    @Override
    public void openInventory() {
        this.markDirty();
    }

    @Override
    public void closeInventory() {
        this.markDirty();
    }

    public InventoryCrafting getCraftingMatrix() {
        if (this.container != null) {
            InventoryCrafting inventoryCrafting
                = new InventoryCrafting((Container) this.container, 3, 3);
            for (int i = 0; i < this.craftingMatrix.length; ++i) {
                inventoryCrafting.setInventorySlotContents(i, this.craftingMatrix[i]);
            }
            return inventoryCrafting;
        }
        return null;
    }

    public void replaceCraftingMatrix(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < this.craftingMatrix.length; ++i) {
            this.craftingMatrix[i] = inventoryCrafting.getStackInSlot(i);
        }
    }

    public boolean isMatrixEmpty() {
        for (int i = 0; i < 9; ++i) {
            if (this.craftingMatrix[i] == null)
                continue;
            return false;
        }
        return true;
    }

    @Override
    public void markDirty() {
        if (!this.worldObj.isRemote) {
            this.isImprinting = false;
            if (this.isMatrixEmpty() && this.imprinterMatrix[0] != null
                && this.imprinterMatrix[1] != null
                && this.imprinterMatrix[0].getItem() instanceof ItemImprinter) {
                ItemStack outputStack = this.imprinterMatrix[0].copy();
                outputStack.stackSize = 1;
                ArrayList<ItemStack> filters = ItemImprinter.getFilters(outputStack);
                boolean filteringItemExists = false;
                for (ItemStack filteredStack : filters) {
                    if (!filteredStack.isItemEqual(this.imprinterMatrix[1]))
                        continue;
                    filters.remove(filteredStack);
                    filteringItemExists = true;
                    break;
                }
                if (!filteringItemExists) {
                    filters.add(this.imprinterMatrix[1]);
                }
                ItemImprinter.setFilters(outputStack, filters);
                this.imprinterMatrix[2] = outputStack;
                this.isImprinting = true;
            }
            if (!this.isImprinting) {
                ItemStack matrixOutput;
                System.out.println("Crafting");
                this.imprinterMatrix[2] = null;
                boolean didCraft = false;
                InventoryCrafting inventoryCrafting = this.getCraftingMatrix();
                if (inventoryCrafting != null
                    && (matrixOutput = CraftingManager.getInstance().findMatchingRecipe(
                            inventoryCrafting, this.worldObj
                        ))
                        != null) {
                    System.out.println("Using crafting grid");
                    this.imprinterMatrix[2] = matrixOutput;
                    didCraft = true;
                }
                if (this.imprinterMatrix[0] != null && !didCraft
                    && this.imprinterMatrix[0].getItem() instanceof ItemImprinter) {
                    System.out.println("Using imprint as grid");
                    ArrayList<ItemStack> filters
                        = ItemImprinter.getFilters(this.imprinterMatrix[0]);
                    for (ItemStack outputStack : filters) {
                        Pair idealRecipe;
                        if (outputStack == null
                            || (idealRecipe = this.getIdealRecipe(outputStack)) == null)
                            continue;
                        ItemStack recipeOutput = (ItemStack) idealRecipe.getKey();
                        System.out.println("Ideal R: " + recipeOutput.toString());
                        if (!(recipeOutput != null & recipeOutput.stackSize > 0))
                            continue;
                        this.imprinterMatrix[2] = recipeOutput;
                        didCraft = true;
                        break;
                    }
                }
                if (!didCraft) {
                    this.imprinterMatrix[2] = null;
                }
            }
        }
    }

    public void onPickUpFromResult(EntityPlayer entityPlayer, ItemStack itemStack) {
    block9: {
    block11: {
    block10: {
        if (itemStack == null)
            break block9;
        if (!this.isImprinting)
            break block10;
        this.imprinterMatrix[0] = null;
        break block9;
    }
        if (this.getIdealRecipe(itemStack) == null)
            break block11;
        ItemStack[] requiredItems
            = (ItemStack[]) ((ItemStack[]) this.getIdealRecipe(itemStack).getValue())
                  .clone();
        if (requiredItems == null)
            break block9;
    block2:
        for (ItemStack searchStack : requiredItems) {
            if (searchStack == null)
                continue;
        block3:
            for (IInventory inventory : this.getAvaliableInventories()) {
                for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                    ItemStack checkStack = inventory.getStackInSlot(i);
                    if (checkStack == null
                        || !searchStack.isItemEqual(checkStack)
                            && (searchStack.getItem() != checkStack.getItem()
                                || searchStack.getItemDamage() >= 0))
                        continue;
                    inventory.decrStackSize(i, 1);
                    break block3;
                }
            }
            for (int i = 0; i < this.containingItems.length; ++i) {
                ItemStack checkStack = this.containingItems[i];
                if (checkStack == null
                    || !searchStack.isItemEqual(checkStack)
                        && (searchStack.getItem() != checkStack.getItem()
                            || searchStack.getItemDamage() >= 0))
                    continue;
                this.decrStackSize(i + 12, 1);
                continue block2;
            }
        }
        break block9;
    }
        try {
            InventoryCrafting inventoryCrafting = this.getCraftingMatrix();
            MinecraftForge.EVENT_BUS.post(
                new ItemCraftedEvent(entityPlayer, itemStack, inventoryCrafting)
            );
            for (int var3 = 0; var3 < inventoryCrafting.getSizeInventory(); ++var3) {
                ItemStack var4 = inventoryCrafting.getStackInSlot(var3);
                if (var4 == null)
                    continue;
                inventoryCrafting.decrStackSize(var3, 1);
                if (!var4.getItem().hasContainerItem())
                    continue;
                ItemStack var5 = var4.getItem().getContainerItem(var4);
                if (var5.isItemStackDamageable()
                    && var5.getItemDamage() > var5.getMaxDamage()) {
                    MinecraftForge.EVENT_BUS.post(
                        new PlayerDestroyItemEvent(entityPlayer, var5)
                    );
                    var5 = null;
                }
                if (var5 == null
                    || var4.getItem().doesContainerItemLeaveCraftingGrid(var4)
                        && entityPlayer.inventory.addItemStackToInventory(var5))
                    continue;
                if (inventoryCrafting.getStackInSlot(var3) == null) {
                    inventoryCrafting.setInventorySlotContents(var3, var5);
                    continue;
                }
                //entityPlayer.func_71021_b(var5);
                //TODO: WTF
                entityPlayer.dropPlayerItemWithRandomChoice(var5, false);
            }
            this.replaceCraftingMatrix(inventoryCrafting);
        } catch (Exception e) {
            System.out.println(
                "Imprinter: Failed to craft item: " + itemStack.getDisplayName()
            );
            e.printStackTrace();
        }
    }
    }

    public Pair getIdealRecipe(ItemStack outputItem) {
        for (Object object : CraftingManager.getInstance().getRecipeList()) {
            ArrayList hasResources;
            Object oreRecipeInput;
            if (!(object instanceof IRecipe)
                || ((IRecipe) object).getRecipeOutput() == null
                || !outputItem.isItemEqual(((IRecipe) object).getRecipeOutput()))
                continue;
            if (object instanceof ShapedRecipes) {
                if (this.hasResource((Object[]) ((ShapedRecipes) object).recipeItems)
                    == null)
                    continue;
                return new Pair(
                    ((IRecipe) object).getRecipeOutput().copy(),
                    ((ShapedRecipes) object).recipeItems
                );
            }
            if (object instanceof ShapelessRecipes) {
                if (this.hasResource((Object[]) ((ShapelessRecipes) object)
                                         .recipeItems.toArray(new ItemStack[1]))
                    == null)
                    continue;
                return new Pair(
                    ((IRecipe) object).getRecipeOutput().copy(),
                    ((ShapelessRecipes) object).recipeItems.toArray(new ItemStack[1])
                );
            }
            if (object instanceof ShapedOreRecipe) {
                ShapedOreRecipe oreRecipe = (ShapedOreRecipe) object;
                oreRecipeInput = (Object[]) ReflectionHelper.getPrivateValue(
                    ShapedOreRecipe.class, oreRecipe, new String[] { "input" }
                );
                hasResources = this.hasResource((Object[]) oreRecipeInput);
                if (hasResources == null)
                    continue;
                return new Pair(
                    ((IRecipe) object).getRecipeOutput().copy(),
                    hasResources.toArray(new ItemStack[1])
                );
            }
            if (!(object instanceof ShapelessOreRecipe))
                continue;
            ShapelessOreRecipe oreRecipe = (ShapelessOreRecipe) object;
            oreRecipeInput = (ArrayList) ReflectionHelper.getPrivateValue(
                ShapelessOreRecipe.class, oreRecipe, new String[] { "input" }
            );
            hasResources = this.hasResource(((ArrayList) oreRecipeInput).toArray());
            if (hasResources == null)
                continue;
            return new Pair(
                ((IRecipe) object).getRecipeOutput().copy(),
                hasResources.toArray(new ItemStack[1])
            );
        }
        return null;
    }

    public ArrayList hasResource(Object[] recipeItems) {
        try {
            TileEntityImprinter dummyImprinter = new TileEntityImprinter();
            NBTTagCompound cloneData = new NBTTagCompound();
            this.writeToNBT(cloneData);
            dummyImprinter.readFromNBT(cloneData);
            ArrayList<ItemStack> actualResources = new ArrayList<ItemStack>();
            int itemMatch = 0;
        block2:
            for (Object obj : recipeItems) {
                if (obj instanceof ItemStack) {
                    ItemStack recipeItem = (ItemStack) obj;
                    actualResources.add(recipeItem.copy());
                    if (recipeItem == null
                        || !this.doesItemExist(recipeItem, dummyImprinter))
                        continue;
                    ++itemMatch;
                    continue;
                }
                if (!(obj instanceof ArrayList))
                    continue;
                ArrayList ingredientsList = (ArrayList) obj;
                Object[] ingredientsArray = ingredientsList.toArray();
                for (int x = 0; x < ingredientsArray.length; ++x) {
                    if (ingredientsArray[x] == null
                        || !(ingredientsArray[x] instanceof ItemStack))
                        continue;
                    ItemStack recipeItem = (ItemStack) ingredientsArray[x];
                    actualResources.add(recipeItem.copy());
                    if (recipeItem == null
                        || !this.doesItemExist(recipeItem, dummyImprinter))
                        continue;
                    ++itemMatch;
                    continue block2;
                }
            }
            return itemMatch >= actualResources.size() ? actualResources : null;
        } catch (Exception e) {
            System.out.println("Failed to find recipes in the imprinter.");
            e.printStackTrace();
            return null;
        }
    }

    private boolean
    doesItemExist(ItemStack recipeItem, TileEntityImprinter dummyImprinter) {
        for (int i = 0; i < dummyImprinter.containingItems.length; ++i) {
            ItemStack checkStack = dummyImprinter.containingItems[i];
            if (checkStack == null
                || !recipeItem.isItemEqual(checkStack)
                    && (recipeItem.getItem() != checkStack.getItem()
                        || recipeItem.getItemDamage() >= 0))
                continue;
            dummyImprinter.decrStackSize(i + 12, 1);
            return true;
        }
        return false;
    }

    private List<IInventory> getAvaliableInventories() {
        ArrayList<IInventory> inventories = new ArrayList<>();
        if (this.searchInventories) {
        block0:
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity tileEntity = VectorHelper.getTileEntityFromSide(
                    this.worldObj, new Vector3(this), direction
                );
                if (tileEntity == null)
                    continue;
                if (tileEntity instanceof TileEntityMulti) {
                    Vector3 mainBlockPosition
                        = ((TileEntityMulti) tileEntity).mainBlockPosition;
                    if (mainBlockPosition == null
                        || !(
                            mainBlockPosition.getTileEntity(this.worldObj)
                                instanceof IInventory
                        ))
                        continue;
                    inventories.add(((IInventory
                    ) mainBlockPosition.getTileEntity(this.worldObj)));
                    continue;
                }
                if (tileEntity instanceof TileEntityChest) {
                    inventories.add(((TileEntityChest) tileEntity));
                    for (int i = 2; i < 6; ++i) {
                        TileEntity chest = VectorHelper.getTileEntityFromSide(
                            this.worldObj,
                            new Vector3(tileEntity),
                            ForgeDirection.getOrientation(2)
                        );
                        if (chest == null || chest.getClass() != tileEntity.getClass())
                            continue;
                        inventories.add(((TileEntityChest) chest));
                        continue block0;
                    }
                    continue;
                }
                if (!(tileEntity instanceof IInventory)
                    || tileEntity instanceof TileEntityImprinter)
                    continue;
                inventories.add(((IInventory) tileEntity));
            }
        }
        return inventories;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("searchInventories", this.searchInventories);
        return new S35PacketUpdateTileEntity(
            xCoord, yCoord, zCoord, getBlockMetadata(), nbt
        );
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        if (this.worldObj.isRemote) {
            NBTTagCompound nbt = pkt.func_148857_g();
            this.searchInventories = nbt.getBoolean("searchInventories");
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList var2 = nbt.getTagList("Items", 10);
        this.craftingMatrix = new ItemStack[9];
        this.imprinterMatrix = new ItemStack[3];
        this.containingItems = new ItemStack[18];
        for (int i = 0; i < var2.tagCount(); ++i) {
            NBTTagCompound var4 = (NBTTagCompound) var2.getCompoundTagAt(i);
            byte var5 = var4.getByte("Slot");
            if (var5 < 0 || var5 >= this.getSizeInventory())
                continue;
            this.setInventorySlotContents(
                var5, ItemStack.loadItemStackFromNBT((NBTTagCompound) var4)
            );
        }
        this.searchInventories = nbt.getBoolean("searchInventories");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList var2 = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) == null)
                continue;
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte) i);
            this.getStackInSlot(i).writeToNBT(var4);
            var2.appendTag((NBTBase) var4);
        }
        nbt.setTag("Items", (NBTBase) var2);
        nbt.setBoolean("searchInventories", this.searchInventories);
    }

    @Override
    public boolean onUse(IArmbot armbot, String[] args) {
        this.markDirty();
        if (this.imprinterMatrix[2] != null) {
            armbot.grabItem(this.imprinterMatrix[2].copy());
            this.onPickUpFromResult(null, this.imprinterMatrix[2]);
            this.imprinterMatrix[2] = null;
        }
        return false;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this
            ? false
            : entityplayer.getDistanceSq(
                  (double) this.xCoord + 0.5,
                  (double) this.yCoord + 0.5,
                  (double) this.zCoord + 0.5
              ) <= 64.0;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        int startIndex = getStartInventorySide(ForgeDirection.getOrientation(var1));
        int size = getSizeInventorySide(ForgeDirection.getOrientation(var1));
        int[] slots = new int[size];
        for (int i = 0; i < size; i++) {
            slots[i] = startIndex + i;
        }
        return slots;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return this.isItemValidForSlot(i, itemstack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return false;
    }

    public int getStartInventorySide(ForgeDirection side) {
        if (side == ForgeDirection.DOWN || side == ForgeDirection.UP) {
            return this.craftingMatrix.length + this.imprinterMatrix.length;
        }
        return this.craftingMatrix.length + 1;
    }

    public int getSizeInventorySide(ForgeDirection side) {
        if (side == ForgeDirection.DOWN || side == ForgeDirection.UP) {
            return this.containingItems.length - 1;
        }
        return 1;
    }
}
