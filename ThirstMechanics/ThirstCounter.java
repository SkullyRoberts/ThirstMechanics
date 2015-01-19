/*     */ package ThirstMechanics;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import com.sk89q.worldguard.protection.ApplicableRegionSet;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class ThirstCounter
/*     */   implements Runnable
/*     */ {
/*     */   ThirstMechanics instance;
/*     */   private double Dmg;
/*  20 */   private List<String> SafeRegions = new ArrayList();
/*     */   private WorldGuardPlugin worldguard;
/*     */   private int counter;
/*     */   private int ran1;
/*     */   private int ran2;
/*     */   boolean messageuse;
/*  26 */   List<String> Messages = new ArrayList();
/*     */ 
/*     */   public ThirstCounter(ThirstMechanics instance, List<String> regions, WorldGuardPlugin wg, boolean messageuse, List<String> messages) {
/*  29 */     this.instance = instance;
/*  30 */     this.Dmg = 1.0D;
/*  31 */     this.SafeRegions = regions;
/*  32 */     this.worldguard = wg;
/*  33 */     this.counter = 0;
/*     */ 
/*  35 */     this.messageuse = messageuse;
/*  36 */     this.Messages = messages;
/*     */ 
/*  38 */     this.ran1 = ((int)(Math.random() * 75.0D));
/*  39 */     this.ran2 = ((int)(Math.random() * 75.0D));
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  45 */     boolean check = false;
/*     */ 
/*  49 */     for (Player p : Bukkit.getOnlinePlayers()) {
/*  50 */       String s = p.getName();
/*  51 */       if (!p.hasPermission("ThirstMechanics.override")) {
/*  52 */         Location loc = p.getLocation();
/*  53 */         for (String r : this.SafeRegions) {
/*  54 */           if (isWithinRegion(loc, r)) {
/*  55 */             check = true;
/*     */           }
/*     */         }
/*     */ 
/*  59 */         if (!check)
/*  60 */           if (((Integer)ThirstMechanics.OnlinePlayers.get(s)).intValue() > 1) {
/*  61 */             ThirstMechanics.OnlinePlayers.put(s, Integer.valueOf(((Integer)ThirstMechanics.OnlinePlayers.get(s)).intValue() - 1));
/*  62 */             p.setLevel(((Integer)ThirstMechanics.OnlinePlayers.get(s)).intValue());
/*  63 */             p.setExp(1.0F);
/*  64 */             if (p.getLevel() == this.ran1) {
/*  65 */               int ran = (int)(Math.random() * this.Messages.size());
/*  66 */               String send = ChatColor.GRAY + (String)this.Messages.get(ran);
/*  67 */               p.sendMessage(send);
/*  68 */             } else if (p.getLevel() == this.ran2) {
/*  69 */               int ran = (int)(Math.random() * this.Messages.size());
/*  70 */               String send = ChatColor.GRAY + (String)this.Messages.get(ran);
/*  71 */               p.sendMessage(send);
/*     */             }
/*     */           }
/*  74 */           else if (p.getHealth() > this.Dmg) {
/*  75 */             if (this.counter == 4) {
/*  76 */               p.damage(this.Dmg);
/*  77 */               p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 1));
/*  78 */               p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 250, 1));
/*  79 */               this.counter = 0;
/*     */             } else {
/*  81 */               this.counter += 1;
/*     */             }
/*     */           }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isWithinRegion(Location loc, String region)
/*     */   {
/*  92 */     WorldGuardPlugin guard = this.worldguard;
/*  93 */     RegionManager manager = guard.getRegionManager(loc.getWorld());
/*  94 */     ApplicableRegionSet set = manager.getApplicableRegions(loc);
/*     */ 
/*  96 */     for (ProtectedRegion r : set) {
/*  97 */       if ((r.getId().equalsIgnoreCase(region)) && 
/*  98 */         (r.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))) {
/*  99 */         return true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 104 */     return false;
/*     */   }
/*     */ }

/* Location:           /Users/Skully/Desktop/ThirstMechanics.jar
 * Qualified Name:     ThirstMechanics.ThirstCounter
 * JD-Core Version:    0.6.2
 */