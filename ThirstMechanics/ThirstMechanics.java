/*    */ package ThirstMechanics;
/*    */ 
/*    */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class ThirstMechanics extends JavaPlugin
/*    */ {
/* 15 */   Logger log = Logger.getLogger("Minecraft");
/* 16 */   static Map<String, Integer> OnlinePlayers = new HashMap();
/* 17 */   static Map<String, Integer> OfflinePlayers = new HashMap();
/* 18 */   static int RestoreLvl = 25;
/* 19 */   static List<String> SafeRegions = new ArrayList();
/* 20 */   static List<String> Messages = new ArrayList();
/* 21 */   static boolean messageuse = false;
/*    */ 
/*    */   public void onEnable()
/*    */   {
/* 25 */     saveDefaultConfig();
/* 26 */     PluginManager manager = getServer().getPluginManager();
/*    */ 
/* 28 */     WorldGuardPlugin worldg = getWorldGuard();
/* 29 */     if (worldg != null) {
/* 30 */       SafeRegions = getConfig().getStringList("Regions");
/*    */     }
/*    */ 
/* 33 */     if (getConfig().getStringList("Messages") != null) {
/* 34 */       Messages = getConfig().getStringList("Messages");
/* 35 */       messageuse = true;
/*    */     }
/*    */ 
/* 38 */     RestoreLvl = getConfig().getInt("RestoreAmount");
/* 39 */     manager.registerEvents(new ThirstListener(this), this);
/* 40 */     getServer().getScheduler().runTaskTimer(this, new ThirstCounter(this, SafeRegions, worldg, messageuse, Messages), 100L, getConfig().getInt("Delay"));
/* 41 */     this.log.info("ThirstMechanics v1.0 Enabled");
/*    */   }
/*    */ 
/*    */   public void onDisable()
/*    */   {
/* 46 */     this.log.info("ThirstMechanics v1.0 Disabled");
/*    */   }
/*    */ 
/*    */   private WorldGuardPlugin getWorldGuard()
/*    */   {
/* 51 */     Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
/*    */ 
/* 54 */     if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
/* 55 */       return null;
/*    */     }
/*    */ 
/* 58 */     return (WorldGuardPlugin)plugin;
/*    */   }
/*    */ }

/* Location:           /Users/Skully/Desktop/ThirstMechanics.jar
 * Qualified Name:     ThirstMechanics.ThirstMechanics
 * JD-Core Version:    0.6.2
 */