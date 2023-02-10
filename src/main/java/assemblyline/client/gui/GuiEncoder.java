package assemblyline.client.gui;

import java.util.ArrayList;

import assemblyline.common.AssemblyLine;
import assemblyline.common.machine.encoder.ContainerEncoder;
import assemblyline.common.machine.encoder.IInventoryWatcher;
import assemblyline.common.machine.encoder.ItemDisk;
import assemblyline.common.machine.encoder.TileEntityEncoder;
import assemblyline.common.network.MessageEncoder;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.TranslationHelper;

public class GuiEncoder extends GuiContainer implements IInventoryWatcher {
    private static final int MAX_COMMANDS = 6;
    private int containerWidth;
    private int containerHeight;
    private TileEntityEncoder tileEntity;
    private ArrayList commands;
    private int minCommand;
    private int selCommand;
    private GuiButton addButton;
    private GuiButton delButton;
    private GuiButton pUpButton;
    private GuiButton pDnButton;
    private GuiTextField commandField;

    public GuiEncoder(InventoryPlayer playerInventory, TileEntityEncoder tileEntity) {
        super((Container) new ContainerEncoder(playerInventory, tileEntity));
        this.ySize = 237;
        this.tileEntity = tileEntity;
        tileEntity.setWatcher(this);
    }

    public void initGui() {
        super.initGui();
        this.allowUserInput = true;
        Keyboard.enableRepeatEvents((boolean) true);
        this.containerWidth = (this.width - this.xSize) / 2;
        this.containerHeight = (this.height - this.ySize) / 2;
        this.addButton = new GuiButton(
            0,
            this.containerWidth + (this.xSize - 25),
            this.containerHeight + 128 + 0,
            18,
            20,
            "+"
        );
        this.delButton = new GuiButton(
            1,
            this.containerWidth + (this.xSize - 43),
            this.containerHeight + 128 + 0,
            18,
            20,
            "-"
        );
        this.pUpButton = new GuiButton(
            2,
            this.containerWidth + (this.xSize - 25),
            this.containerHeight + 46 + 0,
            18,
            20,
            ""
        );
        this.pDnButton = new GuiButton(
            3,
            this.containerWidth + (this.xSize - 25),
            this.containerHeight + 106 + 0,
            18,
            20,
            ""
        );
        this.commandField
            = new GuiTextField(this.fontRendererObj, 8, 129, this.xSize - 52, 18);
        this.buttonList.add(this.addButton);
        this.buttonList.add(this.delButton);
        this.buttonList.add(this.pUpButton);
        this.buttonList.add(this.pDnButton);
        this.commands = new ArrayList();
        this.minCommand = 0;
        this.selCommand = -1;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                ItemStack disk;
                if (this.commandField.getText().equals(""))
                    break;
                if (this.tileEntity != null
                    && (disk = this.tileEntity.getStackInSlot(0)) != null) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setBoolean("create", true);
                    nbt.setString("newCommand", this.commandField.getText());
                    AssemblyLine.NETWORK.sendToServer(
                        new MessageEncoder(new Vector3(this.tileEntity), nbt)
                    );
                }
                this.minCommand = this.commands.size() - 6 + 1;
                if (this.minCommand < 0) {
                    this.minCommand = 0;
                }
                this.selCommand = -1;
                this.commandField.setText("");
                break;
            }
            case 1: {
                if (this.tileEntity == null)
                    break;
                ItemStack disk = this.tileEntity.getStackInSlot(0);
                if (disk != null && this.selCommand >= 0
                    && this.selCommand < this.commands.size()) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setBoolean("create", false);
                    nbt.setInteger("commandToRemove", this.selCommand);
                    AssemblyLine.NETWORK.sendToServer(
                        new MessageEncoder(new Vector3(this.tileEntity), nbt)
                    );
                }
                this.selCommand = -1;
                break;
            }
            case 2: {
                if (this.minCommand <= 0)
                    break;
                --this.minCommand;
                break;
            }
            case 3: {
                if (this.minCommand + 6 >= this.commands.size())
                    break;
                ++this.minCommand;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        GL11.glDisable((int) 2896);
        this.fontRendererObj.drawString(
            TranslationHelper.getLocal("tile.encoder.name"), 68, 8, 0x404040
        );
        this.fontRendererObj.drawString("Disk:", 56, 28, 0x404040);
        GL11.glPushMatrix();
        GL11.glTranslatef(
            (float) (this.pUpButton.xPosition - this.containerWidth + 6),
            (float) (this.pUpButton.yPosition - this.containerHeight + 7),
            (float) 0.0f
        );
        this.fontRendererObj.drawString("^", 1, 1, 0x444444);
        this.fontRendererObj.drawString("^", 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(
            (float) (this.pDnButton.xPosition - this.containerWidth + 6),
            (float) (this.pDnButton.yPosition - this.containerHeight + 7),
            (float) 0.0f
        );
        GL11.glRotatef((float) 180.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
        GL11.glTranslatef((float) -5.0f, (float) -4.0f, (float) 0.0f);
        this.fontRendererObj.drawString("^", -1, -1, 0x444444);
        this.fontRendererObj.drawString("^", 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        if (this.commands != null) {
            this.drawCommands();
        }
        this.commandField.drawTextBox();
    }

    private void drawCommands() {
        for (int i = this.minCommand; i < this.minCommand + 6; ++i) {
            if (i < 0 || i >= this.commands.size())
                continue;
            int relativeCommand = i - this.minCommand;
            String command = ((String) this.commands.get(i)).toUpperCase();
            this.drawCommand(
                command,
                8,
                47 + relativeCommand * (this.fontRendererObj.FONT_HEIGHT + 4),
                this.selCommand == i
            );
        }
    }

    private void drawCommand(String command, int x, int y, boolean selected) {
        if (selected) {
            GuiEncoder.drawOutlineRect(
                x,
                y,
                x + 142,
                y + this.fontRendererObj.FONT_HEIGHT + 4,
                0.0f,
                0.0f,
                0.0f,
                1.0f,
                1.0f,
                1.0f
            );
        }
        this.fontRendererObj.drawString(
            command, x + 3, y + this.fontRendererObj.FONT_HEIGHT / 2 - 1, 0xFFFFFF, false
        );
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        this.commandField.mouseClicked(
            x - this.containerWidth, y - this.containerHeight, button
        );
        if (button == 0) {
            if (x >= this.containerWidth + 8) {
                if (y >= this.containerHeight + 47 + 0) {
                    if (x <= this.containerWidth + (this.xSize - 25)) {
                        if (y <= this.containerHeight + 46 + 80 + 0) {
                            this.listClicked(
                                x - (this.containerWidth + 8),
                                y - (this.containerHeight + 47 + 0)
                            );
                        } else {
                            this.selCommand = -1;
                        }
                    } else {
                        this.selCommand = -1;
                    }
                } else {
                    this.selCommand = -1;
                }
            } else {
                this.selCommand = -1;
            }
        }
    }

    private void listClicked(int relativeX, int relativeY) {
        int itemClicked = relativeY / 13;
        this.selCommand = itemClicked + this.minCommand;
    }

    @Override
    protected void keyTyped(char character, int keycode) {
        if (character != 'e' && character != 'E') {
            super.keyTyped(character, keycode);
        }
        this.commandField.textboxKeyTyped(character, keycode);
        if (keycode == 1) {
            this.mc.thePlayer.closeScreen();
        } else if (keycode == 28) {
            if (this.commandField.isFocused()) {
                this.actionPerformed(this.addButton);
            }
        } else if (keycode == 211) {
            this.actionPerformed(this.delButton);
        } else if (keycode == 201) {
            this.actionPerformed(this.pUpButton);
        } else if (keycode == 209) {
            this.actionPerformed(this.pDnButton);
        } else if (keycode == 200) {
            --this.selCommand;
            if (this.selCommand < -1) {
                this.selCommand = this.commands.size() - 1;
            }
            if (this.selCommand < this.minCommand && this.selCommand >= 0) {
                this.minCommand = this.selCommand;
            }
            if (this.selCommand >= this.minCommand + 6
                && this.selCommand < this.commands.size()) {
                this.minCommand = this.selCommand - 6 + 1;
            }
        } else if (keycode == 208) {
            ++this.selCommand;
            if (this.selCommand >= this.commands.size()) {
                this.selCommand = -1;
            }
            if (this.selCommand >= this.minCommand + 6
                && this.selCommand < this.commands.size()) {
                this.minCommand = this.selCommand - 6 + 1;
            }
            if (this.selCommand < this.minCommand && this.selCommand >= 0) {
                this.minCommand = this.selCommand;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(
            new ResourceLocation("assemblyline", "textures/gui/gui_encoder.png")
        );
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        this.drawTexturedModalRect(
            this.containerWidth, this.containerHeight + 0, 0, 0, this.xSize, this.ySize
        );
        GuiEncoder.drawOutlineRect(
            this.containerWidth + 7,
            this.containerHeight + 46 + 0,
            this.containerWidth + (this.xSize - 25),
            this.containerHeight + 46 + 80 + 0,
            0.0f,
            0.0f,
            0.0f,
            0.5f,
            0.5f,
            0.5f
        );
    }

    public static void drawOutlineRect(
        int x1,
        int y1,
        int x2,
        int y2,
        float rR,
        float rG,
        float rB,
        float lR,
        float lG,
        float lB
    ) {
        Tessellator tesselator = Tessellator.instance;
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glColor4f((float) rR, (float) rG, (float) rB, (float) 1.0f);
        if (rR >= 0.0f && rG >= 0.0f && rB >= 0.0f) {
            tesselator.startDrawingQuads();
            tesselator.addVertex((double) x1, (double) y2, 0.0);
            tesselator.addVertex((double) x2, (double) y2, 0.0);
            tesselator.addVertex((double) x2, (double) y1, 0.0);
            tesselator.addVertex((double) x1, (double) y1, 0.0);
            tesselator.draw();
        }
        GL11.glColor4f((float) lR, (float) lG, (float) lB, (float) 1.0f);
        tesselator.startDrawingQuads();
        tesselator.addVertex((double) x1, (double) y1, 0.0);
        tesselator.addVertex((double) x1, (double) y2, 0.0);
        tesselator.addVertex((double) x1 + 1.0, (double) y2, 0.0);
        tesselator.addVertex((double) x1 + 1.0, (double) y1, 0.0);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.addVertex((double) x2 - 1.0, (double) y1, 0.0);
        tesselator.addVertex((double) x2 - 1.0, (double) y2, 0.0);
        tesselator.addVertex((double) x2, (double) y2, 0.0);
        tesselator.addVertex((double) x2, (double) y1, 0.0);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.addVertex((double) x1, (double) y1, 0.0);
        tesselator.addVertex((double) x1, (double) y1 + 1.0, 0.0);
        tesselator.addVertex((double) x2, (double) y1 + 1.0, 0.0);
        tesselator.addVertex((double) x2, (double) y1, 0.0);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.addVertex((double) x1, (double) y2 - 1.0, 0.0);
        tesselator.addVertex((double) x1, (double) y2, 0.0);
        tesselator.addVertex((double) x2, (double) y2, 0.0);
        tesselator.addVertex((double) x2, (double) y2 - 1.0, 0.0);
        tesselator.draw();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
    }

    private void updateCommands() {
        if (this.commands != null) {
            ItemStack disk;
            this.commands.clear();
            if (this.tileEntity != null
                && (disk = this.tileEntity.getStackInSlot(0)) != null) {
                this.commands = ItemDisk.getCommands(disk);
            }
            if (this.minCommand + 6 >= this.commands.size()) {
                this.minCommand = this.commands.size() - 6;
            }
            if (this.minCommand < 0) {
                this.minCommand = 0;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents((boolean) false);
    }

    @Override
    public void inventoryChanged() {
        this.updateCommands();
    }
}
