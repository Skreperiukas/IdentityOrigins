package draylar.identity.screen.widget;

import draylar.identity.Identity;
import draylar.identity.ModComponents;
import draylar.identity.api.variant.IdentityType;
import draylar.identity.network.DmcNetworkHandler;
import draylar.identity.network.impl.FavoritePackets;
import draylar.identity.network.impl.SwapPackets;
import draylar.identity.screen.IdentityScreen;
import draylar.identity.util.TimeUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public class EntityWidget<T extends LivingEntity> extends PressableWidget {

    private final IdentityType<T> type;
    private final T entity;
    private final int size;
    private boolean active;
    private boolean starred;
    private final IdentityScreen parent;

    public EntityWidget(float x, float y, float width, float height, IdentityType<T> type, T entity, IdentityScreen parent, boolean starred, boolean current) {
        super((int) x, (int) y, (int) width, (int) height, Text.of(""));
        this.type = type;
        this.entity = entity;
        size = (int) (25 * (1 / (Math.max(entity.getHeight(), entity.getWidth()))));
        entity.setGlowing(true);
        this.parent = parent;
        this.starred = starred;
        this.active = current;
        setTooltip(Tooltip.of(type.createTooltipText(entity)));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean bl = mouseX >= (double) this.getX() && mouseX < (double) (this.getX() + this.width) && mouseY >= (double) this.getY() && mouseY < (double) (this.getY() + this.height);

        if(bl) {
            if(button == 0) {
                
            	
            	if(isOnCooldown()) return false;
            	
            	ClientPlayNetworking.send(DmcNetworkHandler.IDENTITY_COOLDOWN, PacketByteBufs.create());
            	ClientPlayNetworking.send(DmcNetworkHandler.IDENTITY_DURATION, PacketByteBufs.create());
            	
            	SwapPackets.sendSwapRequest(type);
                
                
                
                parent.disableAll();
                active = true;
            	
            } else if(button == 1) {
                boolean favorite = !starred;
                starred = favorite;
                FavoritePackets.sendFavoriteRequest(type, favorite);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
//    public void update(T entityType) {
//        
//        System.out.println("Updating EntityWidget: " + entityType); // debug
//    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

	@SuppressWarnings("resource")
	@Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        
        try {
            InventoryScreen.drawEntity(context, getX() + this.getWidth() / 2, (int) (getY() + this.getHeight() * .75f), size, -10, -10, entity);
        } catch (Exception ignored) {
        }
        

        if(active) {
            context.drawTexture(Identity.id("textures/gui/selected.png"), getX(), getY(), getWidth(), getHeight(), 0, 0, 48, 32, 48, 32);
            
        }

        if(starred) {
            context.drawTexture(Identity.id("textures/gui/star.png"), getX(), getY(), 0, 0, 15, 15, 15, 15);
        }
        
        // Cooldown Timer
        if(isOnCooldown()) {
        	
        	// Texture size
            int hourglassWidth = 24;
            int hourglassHeight = 24;
            
            // Offsets
            int textOffsetY = 4;
            
            // Calculate the center of the widget
            int centerX = getX() + (getWidth() / 2);
            int centerY = getY() + (getHeight() / 2);

            // Adjust the position so the texture is centered
            int hourglassX = centerX - (hourglassHeight / 2);
            int hourglassY = centerY - (hourglassWidth / 2);
        	
            String numberText = TimeUtils.ticksToSeconds(ModComponents.getIdentityCooldown(MinecraftClient.getInstance().player)) + "s";
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            int textWidth = textRenderer.getWidth(numberText);
            int textX = centerX - (textWidth / 2);
            int textY = centerY - textOffsetY;
    		
            context.getMatrices().push();
            context.getMatrices().translate(0, 0, 100);
            context.drawTexture(Identity.id("textures/gui/hourglass.png"), hourglassX, hourglassY, hourglassHeight, hourglassWidth, 0, 0, 16, 16, 16, 16);
            context.getMatrices().translate(0, 0, 100);
            context.drawText(
                textRenderer, // The game's font renderer
                numberText, // The number to display
                textX, textY, // X and Y position
                0xFFFFFF, // White color
                true // Drop shadow
            );
            context.getMatrices().pop();
        	
        }
     

    }
    

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void onPress() {
    }

    public IdentityType<T> getEntity() {
    	if(type != null) return type;
    	Identity.LOGGER.warn("[EntityWidget] Something bad is happening here"); // debug
    	return null;
    }
    
    @SuppressWarnings("resource")
	public boolean isOnCooldown() {
    	if(ModComponents.getIdentityCooldown(MinecraftClient.getInstance().player) > 0) return true;
    	return false;
    }

}
