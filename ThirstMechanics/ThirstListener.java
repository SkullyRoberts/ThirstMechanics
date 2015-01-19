/*     */ package ThirstMechanics;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.inventory.FurnaceExtractEvent;
/*     */ import org.bukkit.event.player.PlayerExpChangeEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class ThirstListener
/*     */   implements Listener
/*     */ {
/*     */   ThirstMechanics pl;
/*     */ 
/*     */   public ThirstListener(ThirstMechanics pl)
/*     */   {
/*  21 */     this.pl = pl;
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void DenyNaturalChange(PlayerExpChangeEvent e) {
/*  26 */     e.setAmount(0);
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void DenyDropDeathEvent(EntityDeathEvent e) {
/*  31 */     e.setDroppedExp(0);
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void DenyFuranceExp(FurnaceExtractEvent e) {
/*  36 */     e.setExpToDrop(0);
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void PlayerLeave(PlayerQuitEvent e) {
/*  41 */     Player p = e.getPlayer();
/*  42 */     if (ThirstMechanics.OnlinePlayers.containsKey(p.getName())) {
/*  43 */       ThirstMechanics.OfflinePlayers.put(p.getName(), (Integer)ThirstMechanics.OnlinePlayers.get(p.getName()));
/*  44 */       ThirstMechanics.OnlinePlayers.remove(p.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void PlayerJoin(PlayerLoginEvent e)
/*     */   {
/*  51 */     final Player p = e.getPlayer();
/*     */ 
/*  53 */     if (ThirstMechanics.OfflinePlayers.containsKey(e.getPlayer().getName())) {
/*  54 */       final int xp = ((Integer)ThirstMechanics.OfflinePlayers.get(e.getPlayer().getName())).intValue();
/*  55 */       ThirstMechanics.OnlinePlayers.put(e.getPlayer().getName(), Integer.valueOf(xp));
/*  56 */       ThirstMechanics.OfflinePlayers.remove(e.getPlayer().getName());
/*  57 */       e.getPlayer().setExp(1.0F);
/*  58 */       e.getPlayer().setLevel(xp);
/*  59 */       Runnable fixTask = new Runnable()
/*     */       {
/*     */         public void run() {
/*  62 */           p.setLevel(xp);
/*  63 */           p.setExp(1.0F);
/*     */         }
/*     */       };
/*  66 */       for (int i = 1; i <= 10; i++)
/*  67 */         Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, fixTask, 20L * i);
/*     */     } else {
/*  69 */       ThirstMechanics.OnlinePlayers.put(e.getPlayer().getName(), Integer.valueOf(100));
/*  70 */       e.getPlayer().setExp(1.0F);
/*  71 */       e.getPlayer().setLevel(100);
/*  72 */       Runnable fixTask = new Runnable()
/*     */       {
/*     */         public void run() {
/*  75 */           p.setLevel(100);
/*  76 */           p.setExp(1.0F);
/*     */         }
/*     */       };
/*  79 */       for (int i = 1; i <= 10; i++)
/*  80 */         Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, fixTask, 20L * i);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   private void onDrink(PlayerItemConsumeEvent e) {
/*  86 */     Player p = e.getPlayer();
/*     */ 
/*  88 */     if (ThirstMechanics.OnlinePlayers.containsKey(p.getName())) {
/*  89 */       int addAmount = ThirstMechanics.RestoreLvl;
/*  90 */       int current = ((Integer)ThirstMechanics.OnlinePlayers.get(p.getName())).intValue();
/*  91 */       int fin = current + addAmount;
/*  92 */       if (fin >= 100) {
/*  93 */         fin = 100;
/*     */       }
/*     */ 
/*  96 */       if ((e.getItem().getType() == Material.POTION) && (e.getItem().getDurability() == 0)) {
/*  97 */         ThirstMechanics.OnlinePlayers.remove(p.getName());
/*  98 */         ThirstMechanics.OnlinePlayers.put(p.getName(), Integer.valueOf(fin));
/*  99 */         p.setLevel(fin);
/* 100 */         p.setExp(1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onRespawn(PlayerRespawnEvent e)
/*     */   {
/* 109 */     final Player p = e.getPlayer();
/*     */ 
/* 111 */     if (ThirstMechanics.OnlinePlayers.containsKey(p.getName())) {
/* 112 */       ThirstMechanics.OnlinePlayers.remove(p.getName());
/* 113 */       ThirstMechanics.OnlinePlayers.put(p.getName(), Integer.valueOf(100));
/*     */ 
/* 115 */       Runnable fixTask = new Runnable()
/*     */       {
/*     */         public void run() {
/* 118 */           p.setLevel(100);
/* 119 */           p.setExp(1.0F);
/*     */         }
/*     */       };
/* 122 */       for (int i = 1; i <= 5; i++)
/* 123 */         Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, fixTask, 20L * i);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/Skully/Desktop/ThirstMechanics.jar
 * Qualified Name:     ThirstMechanics.ThirstListener
 * JD-Core Version:    0.6.2
 */