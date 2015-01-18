package com.github.lunatrius.core.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiScreenBase extends GuiScreen {
    protected final GuiScreen parentScreen;

    protected List<GuiButton> buttonList = super.buttonList; // I feel dirty
    protected List<GuiTextField> textFields = new ArrayList<GuiTextField>();

    public GuiScreenBase() {
        this(null);
    }

    public GuiScreenBase(final GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.textFields.clear();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseEvent) throws IOException {
        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                GuiNumericField numericField = (GuiNumericField) button;
                numericField.mouseClicked(mouseX, mouseY, mouseEvent);
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseEvent);
        }

        super.mouseClicked(mouseX, mouseY, mouseEvent);
    }

    @Override
    protected void keyTyped(char character, int code) throws IOException {
        if (code == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }

        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                GuiNumericField numericField = (GuiNumericField) button;
                numericField.keyTyped(character, code);

                if (numericField.isFocused()) {
                    actionPerformed(numericField);
                }
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.textboxKeyTyped(character, code);
        }

        super.keyTyped(character, code);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                GuiNumericField numericField = (GuiNumericField) button;
                numericField.updateCursorCounter();
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.updateCursorCounter();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (GuiTextField textField : this.textFields) {
            textField.drawTextBox();
        }
    }
}
